package com.example.thwissa.fragment.auth.validation

import com.example.thwissa.R

abstract class BaseValidator : Ivalidator {
    companion object {
        fun validate(vararg validators: Ivalidator): ValidateResult {
            validators.forEach {
                val result = it.validate()
                if (!result.isSuccess)
                    return result
            }
            return ValidateResult(true, R.string.text_validation_success)
        }
    }
}