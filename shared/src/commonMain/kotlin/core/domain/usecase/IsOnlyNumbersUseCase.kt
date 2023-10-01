package core.domain.usecase

class IsOnlyNumbersUseCase {

    operator fun invoke(value: String) = value.matches(Regex("[0-9]*"))
}