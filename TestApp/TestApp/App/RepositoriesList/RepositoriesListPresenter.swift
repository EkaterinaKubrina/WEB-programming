//
//  RepositoriesListPresenter.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import RxCocoa
import RxSwift
import RxSwiftExt
import RxSwiftUtilities

protocol RepositoriesListPresenterProtocol: RxPresenter {
    var router: Router<RepositoriesListViewController> { get }
    func buildOutput(with input: RepositoriesListPresenter.Input) -> RepositoriesListPresenter.Output
}

final class RepositoriesListPresenter {
    
    var router: Router<RepositoriesListViewController>
    private let interactor: RepositoriesListInteractor
    
    private let disposeBag = DisposeBag()

    private let repositories: BehaviorRelay<[GithubRepository]> = .init(value: [])
    
    private let repositoryObservable = PublishRelay<GithubRepository>()

    private let activityIndicator = ActivityIndicator()

    init(_ router: Router<RepositoriesListViewController>, _ interactor: RepositoriesListInteractor) {
        self.router = router
        self.interactor = interactor
        
    }
}

extension RepositoriesListPresenter: RepositoriesListPresenterProtocol {
    struct Input {
        let searchText: Observable<String>
        let openDetails: Observable<GithubRepository>
    }

    struct Output {
        let repositories: Driver<[GithubRepository]>
        let repositoryWasChanged: Driver<GithubRepository>
        let isLoading: Driver<Bool>
    }

    func bindInput(_ input: RepositoriesListPresenter.Input) {
        input.searchText
            .flatMapLatest { [unowned self] searchText in
                interactor.searchRepositories(searchText)
                    .asObservable()
                    .trackActivity(activityIndicator)
                    .do(onError: { error in
                        print(error.localizedDescription)
                    })
                    .catchErrorJustComplete()
            }
            .bind(to: repositories)
            .disposed(by: disposeBag)
       
    
        
        input.openDetails
            .bind { [unowned self] repo in
                guard let detailsRoute = self.router as? RepositoryDetailsRoute else {
                    return
                }
                
                detailsRoute.openRepositoryDetails(for: repo){ [unowned self] repository in
                    repositoryObservable.onNext(repository)
                }
            }.disposed(by: disposeBag)
    }

    func configureOutput(_ input: RepositoriesListPresenter.Input) -> RepositoriesListPresenter.Output {
        Output(
            repositories: repositories.asDriver(),
            repositoryWasChanged: repositoryObservable.asDriver(onErrorJustReturn: GithubRepository(id: 1, name: "", fullName: "", owner: GithubUser(id: 1, name: ""), url: "", language: "", description: "", createdAt: Date(), updatedAt: Date())),
            isLoading: activityIndicator.asDriver()
        )
    }
}
