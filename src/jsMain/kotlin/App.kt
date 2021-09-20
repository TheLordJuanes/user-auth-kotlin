import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val App = functionalComponent<RProps> {

    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }
    users.forEach { item ->
        tr {
            td {
                key = item.toString()
                +"${item.username}"
            }
            td {
                key = item.toString()
                +"${item.lastName}"
            }
            td {
                key = item.toString()
                +"${item.firstName}"
            }
            td {
                key = item.toString()
                +"${item.birthDate}"
            }
        }
    }
}