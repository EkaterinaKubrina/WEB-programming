//
//  ViewController.swift
//  TestForTestApp
//
//  Created by katya on 31.01.2023.
//

import UIKit
import RxSwift

class GithubRepositoryCollectionViewController: UICollectionViewController {
    typealias DataSource = UICollectionViewDiffableDataSource<Int, String>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Int, String>
    
    var dataSource: DataSource!
    
    var interactor: GithubRepositoriesInteractor? = nil
    
    private let stringSearch = "ty"
    
    private let disposeBag = DisposeBag()
    
    let publishSubject = PublishSubject<UIImage?>()
    
    let repositories = PublishSubject<[GithubRepository]>()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Repositories"
        
        loadRepositories()
        
        let flowLayout = flowLayout()
        collectionView.collectionViewLayout = flowLayout
        
        let cellRegistration = UICollectionView.CellRegistration { (cell: UICollectionViewListCell, indexPath: IndexPath, itemIdentifier: String) in
            _ = self.repositories.subscribe { array in
                let githubRepository = array[indexPath.item]
                _ = self.publishSubject.element(at: indexPath.item).subscribe { image in
                    var contentConfiguration = cell.defaultContentConfiguration()
                    contentConfiguration.text = githubRepository.fullName
                    contentConfiguration.secondaryText = githubRepository.url
                    contentConfiguration.image = image
                    cell.contentConfiguration = contentConfiguration
                    print(githubRepository.fullName, image.hashValue)
                }.disposed(by: self.disposeBag)
                
                let url = URL(string:githubRepository.owner.avatarUrl ?? "")
                self.downloadImage(from: url!)
            }.disposed(by: self.disposeBag)
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
    
    func getData(from url: URL, completion: @escaping (Data?, URLResponse?, Error?) -> ()) {
        URLSession.shared.dataTask(with: url, completionHandler: completion).resume()
    }
    
    func downloadImage(from url: URL) {
        print("Download Started")
        getData(from: url) { data, response, error in
            guard let data = data, error == nil else { return }
            print(response?.suggestedFilename ?? url.lastPathComponent)
            print("Download Finished")
            // always update the UI from the main thread
            DispatchQueue.main.async() { [weak self] in
                self?.publishSubject.onNext(UIImage(data: data, scale: 2))
            }
        }
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
}

