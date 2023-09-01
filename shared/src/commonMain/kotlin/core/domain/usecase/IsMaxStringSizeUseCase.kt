package core.domain.usecase

class IsMaxStringSizeUseCase() {
    operator fun invoke(value: String, maxSize: Int) = value.length <= maxSize
}