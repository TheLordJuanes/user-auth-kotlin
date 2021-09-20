import react.*
import react.dom.*
import kotlinx.coroutines.*

private val scope = MainScope()

val AppUserLogged = functionalComponent<RProps> {
    val (alerts, setAlerts) = useState(emptyList<Alerts>())

    useEffect {
        scope.launch {
            setAlerts(getAlerts())
        }
    }

    alerts.forEach { item ->
        p {
            key = item.toString()
            +item.alertUserLogged
        }
    }
}