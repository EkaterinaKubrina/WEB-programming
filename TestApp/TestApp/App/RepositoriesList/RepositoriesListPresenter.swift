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
    var router: Router<RepositoriesListCollectionViewController> { get }
    func buildOutput(with input: RepositoriesListPresenter.Input) -> RepositoriesListPresenter.Output
}

final class RepositoriesListPresenter {
    
    var router: Router<RepositoriesListCollectionViewController>
    private let interactor: RepositoriesListInteractor
    
    private let disposeBag = DisposeBag()

    private let repositories: BehaviorRelay<[GithubRepository]> = .init(value: [])
    
    private let activityIndicator = ActivityIndicator()

    init(_ router: Router<RepositoriesListCollectionViewController>, _ interactor: RepositoriesListInteractor) {
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
                    var changedRepositories = repositories.value
                    for i in 0..<changedRepositories.count {
                        if(changedRepositories[i].id == repository.id){
                                changedRepositories[i] = repository
                                break;
                        }
                    }
                    
                    
                    Observable.of(changedRepositories)
                        .bind(to: repositories)
                        .disposed(by: disposeBag)
                    
                }
            }.disposed(by: disposeBag)
    }

    func configureOutput(_ input: RepositoriesListPresenter.Input) -> RepositoriesListPresenter.Output {
        Output(
            repositories: repositories.asDriver(),
            isLoading: activityIndicator.asDriver()
        )
    }
}
