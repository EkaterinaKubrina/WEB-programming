//
//  RepositoriesListCollectionViewController.swift
//  TestApp
//
//  Created by katya on 30.01.2023.
//

import Foundation
import UIKit
import RxSwift
import Differentiator

class RepositoriesListCollectionViewController : BaseCollectionViewController<RepositoriesCollectionListView, RepositoriesListPresenter> {
    
    typealias DataSource = UICollectionViewDiffableDataSource<Int, String>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Int, String>
    
    var dataSource: DataSource!
    
    private let activityIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()
        
        indicator.hidesWhenStopped = true
        
        return indicator
    }()

    override init(collectionViewLayout layout: UICollectionViewLayout,
                  _ presenter: RepositoriesListPresenter) {
        super.init(collectionViewLayout: layout, presenter)
        _view.tableView.reloadData()
        
        navigationItem.rightBarButtonItem = .init(customView: activityIndicator)

        title = "Repositories".localized
        
    }

    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private static let debounceInterval: RxTimeInterval = .milliseconds(1_500)

    private typealias RepositorySection = SectionModel<String, GithubRepository>
    
    private func setListLayout(){
        let listLayout = listLayout()
        collectionView.collectionViewLayout = listLayout
        
        let cellRegistration = UICollectionView.CellRegistration { (cell: UICollectionViewListCell, indexPath: IndexPath, itemIdentifier: String) in
            let githubRepository = GithubRepository.sampleData[indexPath.item]
            var contentConfiguration = cell.defaultContentConfiguration()
            contentConfiguration.text = githubRepository.fullName
            cell.contentConfiguration = contentConfiguration
        }
        
        dataSource = DataSource(collectionView: collectionView) { (collectionView: UICollectionView, indexPath: IndexPath, itemIdentifier: String) in
            return collectionView.dequeueConfiguredReusableCell(using: cellRegistration, for: indexPath, item: itemIdentifier)
        }
        
        var snapshot = Snapshot()
        snapshot.appendSections([0])
        snapshot.appendItems(GithubRepository.sampleData.map { $0.fullName })
        dataSource.apply(snapshot)
        
        collectionView.dataSource = dataSource
    }
    
    override func setupBindings() {
        super.setupBindings()
        
        setListLayout()
        
        let searchBarText = _view.searchBar.rx.text.unwrap()
            .ignore("")
            .debounce(Self.debounceInterval, scheduler: MainScheduler.asyncInstance)
            .distinctUntilChanged()
        
        
        let repositorySelected = _view.tableView.rx
            .modelSelected(GithubRepository.self)
            .asObservable()
        
        
        let input = RepositoriesListPresenter.Input(
            searchText: Observable.merge(searchBarText),
            openDetails: repositorySelected
        )
        
        let output = presenter.buildOutput(with: input)
        
        
    }
    
    private func listLayout() -> UICollectionViewCompositionalLayout {
        var listConfiguration = UICollectionLayoutListConfiguration(appearance: .grouped)

        listConfiguration.showsSeparators = false
        listConfiguration.backgroundColor = .clear
        
        return UICollectionViewCompositionalLayout.list(using: listConfiguration)
    }
}
