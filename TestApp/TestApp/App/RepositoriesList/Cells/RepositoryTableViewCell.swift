//
//  RepositoryTableViewCell.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//

import UIKit

private extension CGFloat {
    static let horizontalMargin: CGFloat = 5
    
    static let verticalMargin: CGFloat = 10
    
    static let verticalSpacing: CGFloat = 5
    
    static let avatarSize: CGFloat = 60
}

class RepositoryTableViewCell: UITableViewCell, ClassIdentifiable {
    
    private let linkLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .preferredFont(forTextStyle: .footnote)
        return label
    }()
    
    private let repoNameLabel: UILabel = {
        let label = UILabel()
        label.font = .preferredFont(forTextStyle: .body)
        return label
    }()
  
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        selectionStyle = .none
        setupSubviews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupSubviews() {
        addSubviews(linkLabel, repoNameLabel)
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        configureSubviews()
    }
    
    func setModel(_ repo: GithubRepository) {
        repoNameLabel.text = repo.fullName
        
        linkLabel.text = repo.url
        
        setNeedsLayout()
    }
    
    private func configureSubviews() {
        
        repoNameLabel.pin
            .top(.verticalMargin)
            .left(.horizontalMargin)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .sizeToFit(.width)
        
        
        linkLabel.pin
            .left(.horizontalMargin)
            .marginLeft(.horizontalMargin)
            .right(.horizontalMargin)
            .below(of: repoNameLabel)
            .marginVertical(.verticalSpacing)
            .sizeToFit(.width)
    }
    
    override func sizeThatFits(_ size: CGSize) -> CGSize {
        
        var fakeSize = size
        
        fakeSize.height = CGFloat.greatestFiniteMagnitude
        
        self.frame.size = fakeSize
        
        layoutSubviews()
        
        var fitSize = size
        
        let maxY = max(linkLabel.frame.maxY, repoNameLabel.frame.maxY)
        
        fitSize.height = maxY + .verticalMargin
        
        return fitSize
    }
}

