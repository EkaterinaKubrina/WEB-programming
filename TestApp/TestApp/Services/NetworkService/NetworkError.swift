//
//  NetworkError.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//

enum NetworkError: Error {
    case errorWithMessage(_ message: String)
    case authFailed
    case noInternet
}
