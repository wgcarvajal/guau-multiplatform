package core.domain.usecase

class ValidateEmailAndPasswordUseCase() {
    operator fun invoke(email: String, password: String): Boolean {

        val mPattern =
            Regex("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return email.isNotEmpty() && mPattern.matches(email) && password.length >= 5

    }
}