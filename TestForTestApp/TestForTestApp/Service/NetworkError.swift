//
//  NetworkError.swift
//  TestForTest
//
//  Created by katya on 31.01.2023.
//

enum NetworkError: Error {
    case errorWithMessage(_ message: String)
    case authFailed
    case noInternet
}
