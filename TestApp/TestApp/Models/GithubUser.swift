//
//  GithubUser.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//

import Foundation

struct GithubUser: Decodable {
    var id: UInt
    
    var name: String
    var avatarUrl: String?
    
    var htmlUrl: String?
    
    enum CodingKeys: String, CodingKey {
        case id
        case name = "login"
        case avatarUrl
        case htmlUrl
    }
}

