import react.*
import react.dom.*
import kotlinx.coroutines.*

private val scope = MainScope()

val AppAlertRegister = functionalComponent<RProps> {
    val (alerts, setAlerts) = useState(emptyList<Alerts>())

    useEffect {
        scope.launch {
            setAlerts(getAlerts())
        }
    }

    alerts.forEach { item ->
        p {
            key = item.toString()
            +item.alertRegister
        }
    }
}