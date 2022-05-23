package com.example.thwissa.fragment.auth.validation.controlValidators

import com.example.thwissa.R
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.ValidateResult

class PhoneNumberValidation (val phonenumber: String) : BaseValidator() {



    override fun validate(): ValidateResult {
        val isValid = (phonenumber.length == 10 && phonenumber.all {
                char -> char.isDigit()
        })

        return ValidateResult(isValid ,
        if (isValid) R.string.text_validation_success else R.string.text_validation_error_password)
    }
}