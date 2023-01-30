//
//  RepositoryDetailsPresenter.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import RxCocoa
import RxSwift
import RxSwiftExt
import RxSwiftUtilities

protocol RepositoryDetailsPresenterProtocol: RxPresenter {
    var router: Router<RepositoryDetailsViewController> { get }
    func buildOutput(with input: RepositoryDetailsPresenter.Input) -> RepositoryDetailsPresenter.Output
}

final class RepositoryDetailsPresenter {
    
    var router: Router<RepositoryDetailsViewController>
    private let interactor: RepositoryDetailsInteractor
    
    private let activityIndicator = ActivityIndicator()
    
    private let disposeBag = DisposeBag()
    
    init(_ router: Router<RepositoryDetailsViewController>, _ interactor: RepositoryDetailsInteractor) {
        self.router = router
        self.interactor = interactor
    }
}

extension RepositoryDetailsPresenter: RepositoryDetailsPresenterProtocol {
    struct Input {
        let changeRepo: Observable<GithubRepository?>
    }
    
    struct Output {
        let repository: Driver<GithubRepository>
        let isLoading: Driver<Bool>
    }
    
    func bindInput(_ input: RepositoryDetailsPresenter.Input) {
        input.changeRepo
            .filter({$0 != nil})
            .subscribe(onNext: { githubRepository in
                self.interactor.onChange(githubRepository!)
                }
            )
            .disposed(by: disposeBag)
    }
    
    func configureOutput(_ input: RepositoryDetailsPresenter.Input) -> RepositoryDetailsPresenter.Output {
        let repository = self.interactor.repository.asDriver(onErrorDriveWith: .empty())
        
        return Output(repository: repository,
                      isLoading: activityIndicator.asDriver())
    }
}
