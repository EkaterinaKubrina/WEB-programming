//
//  UIImageView+Kingfisher.swift
//  TestForTestApp
//
//  Created by katya on 01.02.2023.
//


import Foundation
import Kingfisher
import UIKit

extension UIImageView {
    func setImageUrl(_ url: URL?) {
        guard let url = url else {
            self.image = nil
            return
        }
        
        self.kf.setImage(with: url)
    }
    
    func setImageUrl(_ urlString: String?) {
        guard let urlString = urlString,
              let url = URL(string: urlString) else {
            self.image = nil
            return
        }
        
        self.setImageUrl(url)
    }
}

