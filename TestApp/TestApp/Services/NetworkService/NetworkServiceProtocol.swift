//
//  NetworkServiceProtocol.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//

import Foundation
import RxSwift
import RxSwiftExt

protocol NetworkServiceProtocol {
    func searchRepositories(_ searchString: String) -> Single<[GithubRepository]>
    func getRepositoryDetails(_ ownerName: String, _ repoName: String) -> Single<GithubRepository>
    func getRepositoryReadme(_ ownerName: String, _ repoName: String) -> Single<String>
}
