//
//  RepositoryDetailsViewController.swift
//  TestApp
//
//  Created by katya on 26.01.2023.
//  Copyright © 2023 ___ORGANIZATIONNAME___. All rights reserved.
//

import UIKit
import RxSwift

final class RepositoryDetailsViewController: BaseViewController<RepositoryDetailsView, RepositoryDetailsPresenter> {
    // MARK: - UI Bindings
    private let activityIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()
        
        return indicator
    }()
    
    override init(_ presenter: RepositoryDetailsPresenter) {
        super.init(presenter)
        
        navigationItem.rightBarButtonItem = editButtonItem
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        if(editing){
            _view.repoNameTextField.isEnabled = true
            _view.userNameTextField.isEnabled = true
            _view.updatedAtDatePicker.isEnabled = true
        } else {
            _view.repoNameTextField.isEnabled = false
            _view.userNameTextField.isEnabled = false
            _view.updatedAtDatePicker.isEnabled = false
        }
    }
    
    override func setupBindings() {
        super.setupBindings()
        
        let input = RepositoryDetailsPresenter.Input(
            changeRepo: _view.changeRepo.asObservable()
        )
        let output = presenter.buildOutput(with: input)
        
        output.repository
            .drive(_view.rx.repository)
            .disposed(by: disposeBag)
        
        output.repository
            .map { $0.name }
            .drive(self.rx.title)
            .disposed(by: disposeBag)
        
        
        output.isLoading
            .drive { [unowned self] isLoading in
                if isLoading {
                    activityIndicator.startAnimating()
                } else {
                    activityIndicator.stopAnimating()
                }
            }
            .disposed(by: disposeBag)
    }
}
