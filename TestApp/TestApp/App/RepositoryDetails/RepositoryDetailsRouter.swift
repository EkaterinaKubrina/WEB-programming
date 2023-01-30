//
//  RepositoryDetailsRouter.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

final class RepositoryDetailsRouter: Router<RepositoryDetailsViewController>, RepositoryDetailsRouter.Routes {

    typealias Routes = Any
}

protocol RepositoryDetailsRoute {
    var openRepositoryDetailsTransition: Transition { get }
    func openRepositoryDetails(for repository: GithubRepository, do onChange: @escaping (GithubRepository) -> Void)
}

extension RepositoryDetailsRoute where Self: RouterProtocol {
    func openRepositoryDetails(for repository: GithubRepository, do onChange: @escaping (GithubRepository) -> Void) {
        let router = RepositoryDetailsRouter()
        let viewController = RepositoryDetailsFabric.assembledScreen(repository, onChange, router)
        openWithNextRouter(viewController, nextRouter: router, transition: openRepositoryDetailsTransition)
    }
}
