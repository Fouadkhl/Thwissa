package com.example.thwissa.fragment.auth.validation.controlValidators

import com.example.thwissa.R
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.ValidateResult

class PasswordValidator (val password: String) : BaseValidator() {

    val passwordMinSize = 8
    val passwordMaxSize = 12

    override fun validate(): ValidateResult {
        var isValid = (password.length <= passwordMaxSize && password.length>= passwordMinSize)
        return ValidateResult(isValid ,
        if (isValid) R.string.text_validation_success else R.string.text_validation_error_password)
    }
}