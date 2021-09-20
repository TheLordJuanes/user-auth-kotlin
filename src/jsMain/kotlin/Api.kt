import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.browser.window

val endpoint = window.location.origin

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getUsers(): List<User> {
    return jsonClient.get(endpoint + User.path)
}

suspend fun getAlerts(): List<Alerts> {
    return jsonClient.get(endpoint + Alerts.path)
}