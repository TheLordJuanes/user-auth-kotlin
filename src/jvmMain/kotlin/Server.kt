
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
import java.io.File

const val delimiter: String = ";"
const val fileName: String = "database.txt"

var userLogged : User? = null

var logged: Boolean = false

var users = mutableListOf(
    User("seyerman", "seyer123", "Juan", "Reyes", "01-04-1995", 1),
    User("favellaneda", "fave321", "Fabio", "Avellaneda", "06-09-1987", 2)
)

fun getUserByUsername(username: String): User? {
    for (user in users) {
        if (user.username == username) {
            return user
        }
    }
    return null
}

fun saveUsersInTextFile() {
    var line = "Username;Password;Firstname;Lastname;Birthdate;ID\n"
    for (user in users) {
        line += user.username + delimiter + user.password + delimiter + user.firstName + delimiter + user.lastName + delimiter + user.birthDate + delimiter + user.id + "\n"
    }
    File(fileName).writeText(line)
}

fun loadUsers(data: String) {
    val parts = data.split("\n")
    users.clear()
    for (i in 1 until parts.size) {
        val parts2 = parts[i].split(delimiter)
        val user = User(parts2[0], parts2[1], parts2[3], parts2[3], parts2[4], parts2[5].toInt())
        users.add(user)
    }
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
                val signed: User? = getUserByUsername(username)
                if (signed != null) {
                    if (signed.password == password) {
                        logged = true
                        userLogged = signed
                        call.respondRedirect("/signedIn")
                    } else {
                        call.respondRedirect("/")
                    }
                } else {
                    call.respondRedirect("/")
                }
            }
            post("/signUp") {
                val params = call.receiveParameters()
                val username: String = params["Username"].toString()
                val firstname: String = params["Firstname"].toString()
                val lastname: String = params["Lastname"].toString()
                val password: String = params["Password"].toString()
                val confirmPwd: String = params["ConfirmPwd"].toString()
                val birthdate: String = params["Birthdate"].toString()
                val signed: User? = getUserByUsername(username)
                if(!username.contains(";") && !firstname.contains(";") && !lastname.contains(";") && !password.contains(";") && !confirmPwd.contains(";")){
                if(signed == null) {
                    if (confirmPwd == password) {
                        val user = User(username, password, firstname, lastname, birthdate, users.size + 1)
                        users.add(user)
                        saveUsersInTextFile()
                        call.respondRedirect("/")
                    }
                } else {
                    call.respondRedirect("/register")
                }
                } else {
                    call.respondRedirect("/register")
                }
            }
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
                logged = false
                val data = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
                loadUsers(data)
            }
            get("/register") {
                call.respondText(
                    this::class.java.classLoader.getResource("register.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            get("/signedIn") {
                if(logged) {
                    call.respondText(
                        this::class.java.classLoader.getResource("signedIn.html")!!.readText(),
                        ContentType.Text.Html
                    )
                } else {
                    call.respondRedirect("/")
                }
            }
            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}