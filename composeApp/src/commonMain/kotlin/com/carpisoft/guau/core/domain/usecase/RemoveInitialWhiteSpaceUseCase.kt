package com.carpisoft.guau.core.domain.usecase
class RemoveInitialWhiteSpaceUseCase() {
    operator fun invoke(value: String): String {
        return value.replaceFirstChar {
            if (it.equals(" ")) {
                ""
            } else {
                it.toString()
            }
        }
    }
}