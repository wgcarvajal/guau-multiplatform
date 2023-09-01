package core.domain.usecase

class IsOnlyLettersUseCase() {
    operator fun invoke(value: String) = value.matches(Regex("[a-zA-z\\s]*"))
}