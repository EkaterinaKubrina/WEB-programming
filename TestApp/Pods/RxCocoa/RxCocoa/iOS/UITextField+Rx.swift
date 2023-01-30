//
//  UITextField+Rx.swift
//  RxCocoa
//
//  Created by Krunoslav Zaher on 2/21/15.
//  Copyright © 2015 Krunoslav Zaher. All rights reserved.
//

#if os(iOS) || os(tvOS)

import RxSwift
import UIKit

extension Reactive where Base: UITextField {
    /// Reactive wrapper for `text` property.
    public var text: ControlProperty<String?> {
        return value
    }
    
    /// Reactive wrapper for `text` property.
    public var value: ControlProperty<String?> {
        return base.rx.controlPropertyWithDefaultEvents(
            getter: { updatedAtDatePicker in
                updatedAtDatePicker.text
            },
            setter: { updatedAtDatePicker, value in
                // This check is important because setting text value always clears control state
                // including marked text selection which is imporant for proper input 
                // when IME input method is used.
                if updatedAtDatePicker.text != value {
                    updatedAtDatePicker.text = value
                }
            }
        )
    }
    
    /// Bindable sink for `attributedText` property.
    public var attributedText: ControlProperty<NSAttributedString?> {
        return base.rx.controlPropertyWithDefaultEvents(
            getter: { updatedAtDatePicker in
                updatedAtDatePicker.attributedText
            },
            setter: { updatedAtDatePicker, value in
                // This check is important because setting text value always clears control state
                // including marked text selection which is imporant for proper input
                // when IME input method is used.
                if updatedAtDatePicker.attributedText != value {
                    updatedAtDatePicker.attributedText = value
                }
            }
        )
    }

    /// Bindable sink for `isSecureTextEntry` property.
    public var isSecureTextEntry: Binder<Bool> {
        return Binder(self.base) { updatedAtDatePicker, isSecureTextEntry in
            updatedAtDatePicker.isSecureTextEntry = isSecureTextEntry
        }
    }
    
}

#endif
