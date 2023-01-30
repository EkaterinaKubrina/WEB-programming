//
//  GithubRepository.swift
//  TestForTest
//
//  Created by katya on 30.01.2023.
//

import Foundation

struct GithubRepository: Decodable {
    
    var id: UInt
    
    var name: String
    var fullName: String
    var owner: String
    var url: String
    
    var language: String?
    
    var description: String
    
    var createdAt: Date
    var updatedAt: Date
    
    enum CodingKeys: String, CodingKey {
        case id
        
        case name
        case fullName
        case owner
        case url = "htmlUrl"
        
        case language
        
        case description
        
        case createdAt
        case updatedAt
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        self.id = try container.decode(UInt.self, forKey: .id)
        
        self.name = try container.decode(String.self, forKey: .name)
        self.fullName = try container.decode(String.self, forKey: .fullName)
        self.url = try container.decode(String.self, forKey: .url)
        
        self.language = try? container.decode(String.self, forKey: .language)
        
        self.description = (try? container.decode(String.self, forKey: .description)) ?? ""
        
        self.createdAt = try container.decode(Date.self, forKey: .createdAt)
        self.updatedAt = try container.decode(Date.self, forKey: .updatedAt)
        
        self.owner = try container.decode(String.self, forKey: .owner)
    }
    
    init(id: UInt, name: String, fullName: String, owner: String, url: String, language: String, description: String, createdAt: Date, updatedAt: Date) {
        self.id = id
        
        self.name = name
        self.fullName = fullName
        self.url = url
        
        self.language = language
        
        self.description = description
        
        self.createdAt = createdAt
        self.updatedAt = updatedAt
        
        self.owner = owner
    }
 
    static var sampleData = [
        GithubRepository(id: 1, name: "NameRep", fullName: "FullNameRep", owner: "NameUser", url: "Url", language: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 2, name: "NameRep2", fullName: "FullNameRep2", owner: "NameUser", url: "Url", language: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 3, name: "NameRep3", fullName: "FullNameRep3", owner: "NameUser", url: "Url", language: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 4, name: "NameRep4", fullName: "FullNameRep4", owner: "NameUser", url: "Url", language: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", description: "Description", createdAt: Date(), updatedAt: Date()),
    ]
}
