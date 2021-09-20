import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    render(document.getElementById("usernameLogged")) {
        child(UserLogged)
    }
    render(document.getElementById("users")) {
        child(App)
    }
}