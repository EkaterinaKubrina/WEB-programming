//
//  RepositoryDetailsInteractor.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import RxSwift
import RxCocoa

final class RepositoryDetailsInteractor {
    private let disposeBag = DisposeBag()
    
    private let networkService: NetworkServiceProtocol
    
    var onChange: (GithubRepository) -> Void
    
    
    init(_ networkService: NetworkServiceProtocol, repository: GithubRepository, onChange: @escaping (GithubRepository) -> Void) {
        self.networkService = networkService
        
        self.onChange = onChange
        
        repositoryRelay = .init(value: repository)
    }
    
    private let repositoryRelay: BehaviorRelay<GithubRepository>
    
    var repository: Observable<GithubRepository> {
        repositoryRelay.asObservable()
    }
    
}
