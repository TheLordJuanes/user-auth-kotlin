import react.*
import react.dom.*
import kotlinx.coroutines.*

private val scope = MainScope()

val AppTable = functionalComponent<RProps> {
    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }
    users.forEach { item ->
        tr {
            key = (item.id).toString()
            td {
                +item.username
            }
            td {
                +item.lastName
            }
            td {
                +item.firstName
            }
            td {
                +item.birthDate
            }
        }
    }
}