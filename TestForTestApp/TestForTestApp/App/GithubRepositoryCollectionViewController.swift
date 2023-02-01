//
//  ViewController.swift
//  TestForTestApp
//
//  Created by katya on 31.01.2023.
//

import UIKit
import RxSwift

class GithubRepositoryCollectionViewController: UICollectionViewController {
    typealias DataSource = UICollectionViewDiffableDataSource<Int, UInt>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Int, UInt>
    
    var dataSource: DataSource!
    
    var interactor: GithubRepositoriesInteractor? = nil
    
    private let stringSearch = "ty"
    
    private let disposeBag = DisposeBag()
    
    let publishSubject = PublishSubject<UIImage?>()
    
    let repositories = PublishSubject<[GithubRepository]>()
    
    var githubRepositoriesArray: [GithubRepository] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Repositories"
        
        loadRepositories()
        
        let flowLayout = flowLayout()
        collectionView.collectionViewLayout = flowLayout
        
        let cellRegistration = UICollectionView.CellRegistration { (cell: RepositoryCollectionViewCell, indexPath: IndexPath, itemIdentifier: UInt) in
            let githubRepository = self.githubRepositoriesArray[indexPath.item]
            cell.setModel(githubRepository)
        }
        
        dataSource = DataSource(collectionView: collectionView) { (collectionView: UICollectionView, indexPath: IndexPath, itemIdentifier: UInt) in
            return collectionView.dequeueConfiguredReusableCell(using: cellRegistration, for: indexPath, item: itemIdentifier)
        }
        
        updateSnapshot()
        
        _ = repositories.subscribe { [self] array in
            githubRepositoriesArray = array
            updateSnapshot()
        }.disposed(by: disposeBag)
        
        collectionView.dataSource = dataSource
    }
    
    private func flowLayout() -> UICollectionViewFlowLayout{
        let flowLayout = UICollectionViewFlowLayout()
        flowLayout.minimumLineSpacing = 1
        flowLayout.minimumInteritemSpacing = 1
        flowLayout.itemSize = CGSize(width: (view.frame.size.width/2)-2,
                                     height: (view.frame.size.height/8)-8)
        
        return flowLayout
    }
    
    private func loadRepositories(){
        interactor = GithubRepositoriesInteractor(NetworkService.shared)
        _ = interactor?.searchRepositories(stringSearch).subscribe(
            onSuccess: { arrayGithabs in
                self.repositories.onNext(arrayGithabs)
            },
            onFailure: { Error in
                print(Error.localizedDescription)
            })
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

