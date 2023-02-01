//
//  RepositoriesListViewController.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
import RxSwiftExt
import RxDataSources

final class RepositoriesListViewController: BaseViewController<RepositoriesListView, RepositoriesListPresenter> {
    // MARK: - UI Bindings
    typealias DataSource = UICollectionViewDiffableDataSource<Int, UInt>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Int, UInt>
    
    var dataSource: DataSource!
    
    var githubRepositoriesArray: [GithubRepository] = []
    
    private let activityIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()
        
        indicator.hidesWhenStopped = true
        
        return indicator
    }()
    
    override init(_ presenter: RepositoriesListPresenter) {
        super.init(presenter)
        
        navigationItem.rightBarButtonItem = .init(customView: activityIndicator)
        
        title = "Repositories".localized
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private static let debounceInterval: RxTimeInterval = .milliseconds(1_500)
    
    override func setupBindings() {
        super.setupBindings()
        
        let cellRegistration = UICollectionView.CellRegistration { (cell: RepositoryCollectionViewCell, indexPath: IndexPath, itemIdentifier: UInt) in
            let githubRepository = self.githubRepositoriesArray[indexPath.item]
            cell.setModel(githubRepository)
        }
        
        dataSource = DataSource(collectionView: _view.collectionView!) { (collectionView: UICollectionView, indexPath: IndexPath, itemIdentifier: UInt) in
            return collectionView.dequeueConfiguredReusableCell(using: cellRegistration, for: indexPath, item: itemIdentifier)
        }
        
        updateSnapshot()
        
        _view.collectionView!.dataSource = dataSource
        
        let searchBarText = _view.searchBar.rx.text.unwrap()
            .ignore("")
            .debounce(Self.debounceInterval, scheduler: MainScheduler.asyncInstance)
            .distinctUntilChanged()
        
        let refresh = _view.refreshControl.rx.controlEvent(.valueChanged)
            .map { _ in () }
            .withLatestFrom(searchBarText)
        
        
        let repositorySelected = _view.collectionView!.rx.itemSelected.map { IndexPath in
            return self.githubRepositoriesArray[IndexPath.item]
        }.asObservable()
        
        
        let input = RepositoriesListPresenter.Input(
            searchText: Observable.merge(searchBarText, refresh),
            openDetails: repositorySelected
        )
        
        let output = presenter.buildOutput(with: input)
        
        output.repositories.drive({ arrayObservable in
            arrayObservable.subscribe { [self] array in
                githubRepositoriesArray = array
                updateSnapshot()
            }
        }).disposed(by: disposeBag)
        
        output.repositoryWasChanged.drive { [self] repository in
            for i in 0..<githubRepositoriesArray.count {
                if(githubRepositoriesArray[i].id == repository.id){
                    githubRepositoriesArray[i] = repository
                    updateSnapshot(reloading: [repository.id])
                    break;
                }
            }
            
        }.disposed(by: disposeBag)
        
        output.isLoading
            .drive { [unowned self] isLoading in
                if isLoading {
                    activityIndicator.startAnimating()
                } else {
                    activityIndicator.stopAnimating()
                    _view.refreshControl.endRefreshing()
                }
            }
            .disposed(by: disposeBag)
    }
    
    func updateSnapshot(reloading idsThatChanged: [UInt] = []) {
        let ids = idsThatChanged.filter { id in githubRepositoriesArray.contains(where: { $0.id == id }) }
        var snapshot = Snapshot()
        snapshot.appendSections([0])
        snapshot.appendItems(githubRepositoriesArray.map { $0.id })
        if !ids.isEmpty {
            snapshot.reloadItems(ids)
        }
        dataSource.apply(snapshot)
    }
    
}
