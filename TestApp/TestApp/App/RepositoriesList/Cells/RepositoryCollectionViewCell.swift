//
//  RepositoryCollectionViewCell.swift
//  TestApp
//
//  Created by katya on 01.02.2023.
//

import UIKit
import PinLayout

private extension CGFloat {
    static let horizontalMargin: CGFloat = 5
    
    static let verticalMargin: CGFloat = 10
    
    static let verticalSpacing: CGFloat = 5
    
    static let avatarSize: CGFloat = 50
}

class RepositoryCollectionViewCell: UICollectionViewCell {
    private let repoNameLabel: UILabel = {
        let label = UILabel()
        label.font = .preferredFont(forTextStyle: .body)
        return label
    }()
    
    private let linkLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .preferredFont(forTextStyle: .caption1)
        return label
    }()
    
    private let imageRepo: UIImageView = {
        let image = UIImageView()
        image.layer.cornerRadius = 25
        image.layer.masksToBounds = true
        return image
    }()
    
  
    override init(frame: CGRect){
        super.init(frame: .zero)
        setupSubviews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupSubviews() {
        addSubview(linkLabel)
        addSubview(repoNameLabel)
        addSubview(imageRepo)
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        configureSubviews()
    }
    
    func setModel(_ repo: GithubRepository) {
        repoNameLabel.text = repo.fullName
        
        linkLabel.text = repo.url
        
        imageRepo.setImageUrl(repo.owner.avatarUrl)
        
        setNeedsLayout()
    }
    
    private func configureSubviews() {
        
        repoNameLabel.pin
            .top(.verticalMargin)
            .left(.horizontalMargin)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .sizeToFit(.width)
        
        imageRepo.pin
            .left(.horizontalMargin)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .below(of: repoNameLabel)
            .marginVertical(.verticalSpacing)
            .width(.avatarSize)
            .height(.avatarSize)
        
        linkLabel.pin
            .after(of: imageRepo)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .below(of: repoNameLabel)
            .marginVertical(.verticalSpacing)
            .sizeToFit(.width)
    }
    
    override func sizeThatFits(_ size: CGSize) -> CGSize {
        
        configureSubviews()
        
        var fitSize = size
        
        let maxY = max(imageRepo.frame.maxY, linkLabel.frame.maxY)
        
        fitSize.height = maxY + .verticalMargin
        
        return fitSize
    }
}
