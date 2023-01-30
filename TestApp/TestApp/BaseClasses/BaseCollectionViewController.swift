//
//  BaseCollectionViewController.swift
//  TestApp
//
//  Created by katya on 30.01.2023.
//

import UIKit
import RxSwift

class BaseCollectionViewController<View: UIView, Presenter: RxPresenter>: UICollectionViewController {
    
    let presenter: Presenter
    
    var _view: View {
        get {
            view as! View // swiftlint:disable:this force_cast
        }
        set {
            view = newValue
        }
    }
    
    let disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupBindings()
        hideKeyboardWhenTappedAround()
    }
    
    func setupBindings() { }
    
    override func loadView() {
        view = View()
    }
    
    init(_ presenter: Presenter) {
        self.presenter = presenter
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

