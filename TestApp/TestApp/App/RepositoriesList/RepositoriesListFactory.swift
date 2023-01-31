//
//  RepositoriesListFactory.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import UIKit

final class RepositoriesListFabric {
    class func assembledScreen(_ router: RepositoriesListRouter = .init(),
                               networkSerivce: NetworkServiceProtocol = NetworkService.shared) -> RepositoriesListCollectionViewController {
        let interactor = RepositoriesListInteractor(networkSerivce)
        let presenter = RepositoriesListPresenter(router, interactor)
        
        let flowLayout = UICollectionViewFlowLayout()
        
        let viewController = RepositoriesListCollectionViewController(collectionViewLayout: flowLayout, presenter)
        router.viewController = viewController
        return viewController
    }
}
