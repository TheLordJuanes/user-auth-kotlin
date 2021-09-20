import kotlinx.serialization.Serializable

@Serializable
data class Alerts(
    var alertUserLogged: String,
    var alertLogin: String,
    var alertRegister: String,
    var alertSignedIn: String
) {
    companion object {
        const val path = "/alerts"
    }
}
