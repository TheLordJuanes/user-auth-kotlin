
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val users = mutableListOf(
    User("seyerman", "seyer123", "Juan", "Reyes", "01/04/1995"),
    User("favellaneda", "fave321", "Fabio", "Avellaneda", "06/09/1987")
)

fun getUserByUsername(username: String): User? {
    for (user in users) {
        if (user.username == username) {
            return user
        }
    }
    return null
}

fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            route(User.path) {
                get {
                    call.respond(users)
                }
                post {
                    users += call.receive<User>()
                    call.respond(HttpStatusCode.OK)
                }
            }
            post("/signIn") {
                val params = call.receiveParameters()
                val username: String = params["Username"].toString()
                val password: String = params["Password"].toString()
                var signed: User? = getUserByUsername(username)
                if (signed != null) {
                    if (signed.password == password) {
                        call.respondRedirect("/signedIn")
                    } else {
                        //TO DO
                        //alert("The password is wrong!")
                        call.respondRedirect("/")
                    }
                } else {
                    //TO DO
                    //alert("This username doesn't exist!")
                    call.respondRedirect("/")
                }
            }
            post("/signUp") {
                //TO DO
            }
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            get("/register") {
                call.respondText(
                    this::class.java.classLoader.getResource("register.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            get("/signedIn") {
                call.respondText(
                    this::class.java.classLoader.getResource("signedIn.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}