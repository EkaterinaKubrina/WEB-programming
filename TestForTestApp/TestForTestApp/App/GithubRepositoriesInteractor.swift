//
//  GithubRepositoriesInteractor.swift
//  TestForTest
//
//  Created by katya on 31.01.2023.
//

import RxSwift
import RxCocoa

final class GithubRepositoriesInteractor {
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

