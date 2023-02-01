//
//  RepositoriesListView.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import UIKit
import RxSwift
import PinLayout

final class RepositoriesListView: BaseView {
    
    // MARK: - UI Elements
    
    let searchBar: UISearchBar = {
        let searchBar = UISearchBar()
        
        searchBar.autocorrectionType = .no
        
        searchBar.autocapitalizationType = .none
        
        return searchBar
    }()
    
    let tableView: UITableView = {
        let tableView = UITableView(frame: .zero, style: .plain)
        
        tableView.register(cellType: RepositoryTableViewCellDetails.self)
        tableView.tableFooterView = UIView()
        tableView.rowHeight = UITableView.automaticDimension
        
        return tableView
    }()
    
    var collectionView: UICollectionView? = nil
    
    let refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        
        return refreshControl
    }()
    
    // MARK: - Lifecycle
    
    override func setupSubviews() {
        super.setupSubviews()
        
        let flowLayout = UICollectionViewFlowLayout()
        flowLayout.minimumLineSpacing = 1
        flowLayout.minimumInteritemSpacing = 1
        flowLayout.itemSize = CGSize(width: (UIScreen.main.bounds.width/2)-2,
                                     height: (UIScreen.main.bounds.height/8)-8)
        collectionView = UICollectionView(frame: .zero, collectionViewLayout: flowLayout)
        
        backgroundColor = .white
        
        addSubviews(searchBar, tableView, collectionView!)
        
        tableView.refreshControl = refreshControl
    }
    
    override func configureSubviews() {
        super.configureSubviews()
        
        searchBar.pin
            .top(pin.safeArea)
            .horizontally(pin.safeArea)
            .height(.searchBarHeight)
        
        collectionView?.pin
            .below(of: searchBar)
            .horizontally(pin.safeArea)
            .bottom(pin.safeArea)
    }
}

// MARK: - Constants
private extension CGFloat {
    static let searchBarHeight: CGFloat = 60
}
