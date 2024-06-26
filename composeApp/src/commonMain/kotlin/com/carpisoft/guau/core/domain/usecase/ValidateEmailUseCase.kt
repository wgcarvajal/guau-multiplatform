package com.carpisoft.guau.core.domain.usecase

class ValidateEmailUseCase {

    operator fun invoke(email: String): Boolean {
        val mPattern =
            Regex("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return mPattern.matches(email)
    }
}