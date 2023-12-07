package initialsetup.domain.model

data class CenterResp(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val image: String?
)
