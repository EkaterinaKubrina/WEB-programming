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
    var owner: GithubUser
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
        
        self.owner = try container.decode(GithubUser.self, forKey: .owner)
    }
    
    init(id: UInt, name: String, fullName: String, owner: GithubUser, url: String, language: String, description: String, createdAt: Date, updatedAt: Date) {
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
        GithubRepository(id: 1, name: "NameRep", fullName: "FullNameRep", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 2, name: "NameRep2", fullName: "FullNameRep2", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 3, name: "NameRep3", fullName: "FullNameRep3", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 4, name: "NameRep4", fullName: "FullNameRep4", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 5, name: "NameRep5", fullName: "FullNameRep5", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 6, name: "NameRep6", fullName: "FullNameRep6", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url",  language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 7, name: "NameRep7", fullName: "FullNameRep7", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
        GithubRepository(id: 8, name: "NameRep8", fullName: "FullNameRep8", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        
            GithubRepository(id: 9, name: "NameRep9", fullName: "FullNameRep9", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 10, name: "NameRep10", fullName: "FullNameRep10", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 11, name: "NameRep11", fullName: "FullNameRep11", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 12, name: "NameRep12", fullName: "FullNameRep12", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 13, name: "NameRep13", fullName: "FullNameRep13", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 14, name: "NameRep14", fullName: "FullNameRep14", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url",  language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 15, name: "NameRep15", fullName: "FullNameRep15", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",  htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
            
            GithubRepository(id: 16, name: "NameRep16", fullName: "FullNameRep16", owner: GithubUser(id: 1, name: "Ivan", avatarUrl: "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", htmlUrl: "url"), url: "Url", language: "french", description: "Description", createdAt: Date(), updatedAt: Date()),
        ]
}
