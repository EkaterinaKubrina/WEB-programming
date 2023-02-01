//
//  RepositoriesCollectionListView.swift
//  TestApp
//
//  Created by katya on 30.01.2023.
//

//import UIKit
//import RxSwift
//import PinLayout
//
//final class RepositoriesCollectionListView: UICollectionView {
//    
//    // MARK: - UI Elements
//    
//    let searchBar: UISearchBar = {
//        let searchBar = UISearchBar()
//        
//        searchBar.autocorrectionType = .no
//        
//        searchBar.autocapitalizationType = .none
//        
//        return searchBar
//    }()
//    
//    let tableView: UITableView = {
//        let tableView = UITableView(frame: .zero, style: .plain)
//        
//        tableView.register(cellType: RepositoryTableViewCellDetails.self)
//        tableView.tableFooterView = UIView()
//        tableView.rowHeight = UITableView.automaticDimension
//        
//        return tableView
//    }()
//    
//    override init(frame: CGRect, collectionViewLayout layout: UICollectionViewLayout) {
//        super.init(frame: frame, collectionViewLayout: layout)
//        
//        setupSubviews()
//        setupBindings()
//    }
//    
//    required convenience init?(coder: NSCoder) {
//        self.init(frame: .zero)
//    }
//    
//    override func layoutSubviews() {
//        super.layoutSubviews()
//        configureSubviews()
//    }
//    
//    // MARK: - Lifecycle
//    
//    func setupSubviews() {
//        backgroundColor = .white
//        
//        addSubviews(searchBar, tableView)
//        
//        tableView.refreshControl = refreshControl
//    }
//    
//    func setupBindings() { }
//    
//    func configureSubviews() {
//        searchBar.pin
//            .top(pin.safeArea)
//            .horizontally(pin.safeArea)
//            .height(60)
//        
//        tableView.pin
//            .below(of: searchBar)
//            .horizontally(pin.safeArea)
//            .bottom(pin.safeArea)
//    }
//    
//}
//
//
