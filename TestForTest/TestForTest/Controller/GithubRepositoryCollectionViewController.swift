//
//  GithubRepositoryCollectionViewController.swift
//  TestForTest
//
//  Created by katya on 30.01.2023.
//

import UIKit

class GithubRepositoryCollectionViewController: UICollectionViewController {
    typealias DataSource = UICollectionViewDiffableDataSource<Int, String>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Int, String>
    
    var dataSource: DataSource!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let listLayout = listLayout()
        collectionView.collectionViewLayout = listLayout
        
        let cellRegistration = UICollectionView.CellRegistration { (cell: UICollectionViewListCell, indexPath: IndexPath, itemIdentifier: String) in
            let githubRepository = GithubRepository.sampleData[indexPath.item]
            var contentConfiguration = cell.defaultContentConfiguration()
            contentConfiguration.text = githubRepository.fullName
            
            
            var imageSellConfiguration = self.imageSellConfiguration(for: githubRepository)
            cell.accessories = [ .customView(configuration: imageSellConfiguration), .disclosureIndicator(displayed: .always) ]
            
            
            contentConfiguration.secondaryText = githubRepository.url
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
    
    private func imageSellConfiguration(for githubRepository: GithubRepository) -> UICellAccessory.CustomViewConfiguration {
        let image = UIImageView()
        image.layer.masksToBounds = true
        image.layer.cornerRadius = 25
        image.sizeThatFits(CGSize(width: 20, height: 20))
        image.setImageUrl(githubRepository.language)
        return UICellAccessory.CustomViewConfiguration(customView: image, placement: .leading(displayed: .always))
    }
    
    private func listLayout() -> UICollectionViewCompositionalLayout {
        var listConfiguration = UICollectionLayoutListConfiguration(appearance: .grouped)
        listConfiguration.showsSeparators = false
        listConfiguration.backgroundColor = .clear
        return UICollectionViewCompositionalLayout.list(using: listConfiguration)
    }
  
}
