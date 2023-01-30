//
//  RepositoryDetailsFactory.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

final class RepositoryDetailsFabric {
    class func assembledScreen(_ repository: GithubRepository,
                               _ onChange: @escaping (GithubRepository)->Void,
                               _ router: RepositoryDetailsRouter = .init(),
                               networkService: NetworkServiceProtocol = NetworkService.shared) -> RepositoryDetailsViewController {
        let interactor = RepositoryDetailsInteractor(networkService, repository: repository, onChange: onChange)
        let presenter = RepositoryDetailsPresenter(router, interactor)
        let viewController = RepositoryDetailsViewController(presenter)
        router.viewController = viewController
        return viewController
    }
}
