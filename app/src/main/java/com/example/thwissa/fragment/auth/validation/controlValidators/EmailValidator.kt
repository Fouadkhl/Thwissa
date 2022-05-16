package com.example.thwissa.fragment.auth.validation.controlValidators

import android.util.Patterns
import com.example.thwissa.R
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.ValidateResult

class EmailValidator (val email: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        var isValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return ValidateResult(isValid ,
        if (isValid) R.string.text_validation_success else R.string.text_validation_error_email)
    }
}