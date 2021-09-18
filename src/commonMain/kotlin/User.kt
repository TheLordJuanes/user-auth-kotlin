import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String, val firstName: String, val lastName: String, val birthDate: String) {
    val id: Int = username.hashCode()
    companion object {
        const val path = "/users"
    }
}