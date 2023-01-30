//
//  RepositoryTableViewCellDetails.swift
//  TestApp
//
//  Created by katya on 30.01.2023.
//

import UIKit


private extension CGFloat {
    static let horizontalMargin: CGFloat = 5
    
    static let verticalMargin: CGFloat = 10
    
    static let verticalSpacing: CGFloat = 5
    
    static let avatarSize: CGFloat = 50
}

class RepositoryTableViewCellDetails: UITableViewCell, ClassIdentifiable {
    
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
        image.layer.masksToBounds = true
        image.setRadius(radius: 25)
        return image
    }()
    
    private let repoDescriptionLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .preferredFont(forTextStyle: .footnote)
        return label
    }()
    
  
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        selectionStyle = .default
        setupSubviews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupSubviews() {
        addSubviews(linkLabel, repoNameLabel, repoDescriptionLabel, imageRepo)
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        configureSubviews()
    }
    
    func setModel(_ repo: GithubRepository) {
        repoNameLabel.text = repo.fullName
        
        linkLabel.text = repo.url
        
        repoDescriptionLabel.text = repo.description
        
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
        
        repoDescriptionLabel.pin
            .after(of: imageRepo)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .below(of: repoNameLabel)
            .marginVertical(.verticalSpacing)
            .sizeToFit(.width)
        
        linkLabel.pin
            .after(of: imageRepo)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .below(of: repoDescriptionLabel)
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
