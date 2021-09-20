import kotlinx.serialization.Serializable

@Serializable
data class Alerts(
    var alertUserLogged: String,
    var alertLogin: String,
    var alertRegister: String
) {
    companion object {
        const val path = "/alerts"
    }
}
