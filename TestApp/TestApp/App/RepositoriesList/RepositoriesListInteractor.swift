//
//  RepositoriesListInteractor.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import RxSwift
import RxCocoa

final class RepositoriesListInteractor {
    private let disposeBag = DisposeBag()
    
    private let networkService: NetworkServiceProtocol
    
    init(_ networkService: NetworkServiceProtocol) {
        self.networkService = networkService
    }
    
    func searchRepositories(_ searchString: String) -> Single<[GithubRepository]> {
        networkService
            .searchRepositories(searchString)
    }
}
