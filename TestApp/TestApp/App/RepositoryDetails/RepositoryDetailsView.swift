//
//  RepositoryDetailsView.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import UIKit
import Foundation
import RxSwift
import RxCocoa

private extension CGFloat {
    
    static let repoNameTopMargin: CGFloat = 20
    
    static let editingFieldsHorizontalMargin: CGFloat = 40
    
    static let labelVerticalSpacing: CGFloat = 20
    
    static let EditFieldsVerticalSpacing: CGFloat = 10
    
    static let labelHorizontalMargin: CGFloat = 20
    
    static let dateLabelsHorizontalSpacing: CGFloat = 20
    
}

final class RepositoryDetailsView: BaseView {
    
    // MARK: - UI Elements
    
    let repoName: UILabel = {
        let label = UILabel()
        
        label.font = .systemFont(ofSize: 20, weight: .semibold)
        
        return label
    }()
    
    let repoNameTextField: UITextField = {
        let repoNameTextField = UITextField()
        
        repoNameTextField.borderStyle = .roundedRect
        repoNameTextField.isEnabled = false
        repoNameTextField.addTarget(nil, action: #selector(editRepoName(_:)), for: .editingChanged)
        
        return repoNameTextField
    }()
    
    let userNameLabel: UILabel = {
        let label = UILabel()
        
        label.font = .systemFont(ofSize: 16, weight: .medium)
        
        label.numberOfLines = 0
        
        return label
    }()
    
    let userNameTextField: UITextField = {
        let userNameTextField = UITextField()
        
        userNameTextField.borderStyle = .roundedRect
        userNameTextField.isEnabled = false
        userNameTextField.addTarget(nil, action: #selector(editUserName(_:)), for: .editingChanged)
        
        return userNameTextField
    }()
    
    let updatedAtLabel: UILabel = {
        let label = UILabel()
        
        label.font = .systemFont(ofSize: 15, weight: .medium)
        
        return label
    }()
    
    let updatedAtDatePicker: UIDatePicker = {
        let updatedAtDatePicker = UIDatePicker()
        
        updatedAtDatePicker.preferredDatePickerStyle = .inline
        updatedAtDatePicker.isEnabled = false
        updatedAtDatePicker.addTarget(nil, action: #selector(editDate(_:)), for: .valueChanged)
        return updatedAtDatePicker
    }()
    
    required convenience init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    // MARK: - Lifecycle
    
    var changeRepo: PublishRelay<GithubRepository?> = .init()
    
    var repository: GithubRepository? = nil {
        didSet {
            updateUI()
        }
    }
    
    static let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        
        formatter.dateFormat = "MM-dd-yyyy HH:mm"
        
        return formatter
    }()
    
    private func updateUI() {
        guard let nameRepository = repository?.name else {
            return
        }
        repoName.text = "Repository: \(nameRepository)"
        repoNameTextField.text = nameRepository
        
        
        guard let nameUser = repository?.owner.name  else {
            return
        }
        userNameLabel.text = "Owner: \(nameUser)"
        userNameTextField.text = nameUser
        
        var updatedAtString = "Unknown".localized
        
        if let updatedAt = repository?.updatedAt {
            updatedAtString = Self.dateFormatter.string(from: updatedAt)
            updatedAtDatePicker.date = updatedAt
        }
        
        updatedAtLabel.text = "Updated at: %@".localizedWithFormat(updatedAtString)
        
        setNeedsLayout()
    }
    
    override func setupSubviews() {
        super.setupSubviews()
        
        backgroundColor = .white
        
        addSubviews(repoName,
                    repoNameTextField,
                    userNameLabel,
                    userNameTextField,
                    updatedAtLabel,
                    updatedAtDatePicker)
    }
    
    @objc func editDate(_ sender: UIDatePicker) {
        repository?.updatedAt = updatedAtDatePicker.date
        changeRepo.on(.next(repository))
    }
    
    
    @objc func editUserName(_ sender: UITextField) {
        if(userNameTextField.text != ""){
            let repo = repository?.name
            repository?.owner.name = userNameTextField.text ?? "Unknown"
            repository?.fullName = "\(userNameTextField.text ?? "Unknown")/\(repo ?? "Unknown")"
            changeRepo.onNext(repository)
        }
    }
    
    
    @objc func editRepoName(_ sender: UITextField) {
        if(repoNameTextField.text != ""){
            let name = repository?.owner.name
            repository?.name = repoNameTextField.text ?? "Unknown"
            repository?.fullName = "\(name ?? "Unknown")/\(repoNameTextField.text ?? "Unknown")"
            changeRepo.on(.next(repository))
        }
    }
    
    override func configureSubviews() {
        
        repoName.pin
            .top(pin.safeArea)
            .marginTop(.labelVerticalSpacing)
            .left(.labelHorizontalMargin)
            .right(.labelHorizontalMargin)
            .sizeToFit(.width)
        
        repoNameTextField.pin
            .below(of: repoName)
            .marginTop(.EditFieldsVerticalSpacing)
            .horizontally(.editingFieldsHorizontalMargin)
            .sizeToFit(.width)
        
        userNameLabel.pin
            .below(of: repoNameTextField)
            .marginTop(.labelVerticalSpacing)
            .horizontally(.labelHorizontalMargin)
            .sizeToFit(.width)
        
        userNameTextField.pin
            .below(of: userNameLabel)
            .marginTop(.EditFieldsVerticalSpacing)
            .horizontally(.editingFieldsHorizontalMargin)
            .sizeToFit(.width)
        
        updatedAtLabel.pin
            .below(of: userNameTextField)
            .marginTop(.labelVerticalSpacing)
            .left(.labelHorizontalMargin)
            .right(.labelVerticalSpacing)
            .sizeToFit(.width)
        
        updatedAtDatePicker.pin
            .below(of: updatedAtLabel)
            .marginTop(.EditFieldsVerticalSpacing)
            .horizontally(.editingFieldsHorizontalMargin)
            .sizeToFit(.width)
        
        
    }
}

extension Reactive where Base: RepositoryDetailsView {
    var repository: Binder<GithubRepository?> {
        .init(self.base) { view, repo in
            view.repository = repo
        }
    }
}
