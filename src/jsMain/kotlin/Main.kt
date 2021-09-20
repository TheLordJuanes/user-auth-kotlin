import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    if (document.getElementById("users") != null) {
        render(document.getElementById("users")) {
            child(AppTable)
        }
    }
    if (document.getElementById("usernameLogged") != null) {
        render(document.getElementById("usernameLogged")) {
            child(AppUserLogged)
        }
    }
    if (document.getElementById("loginAlert") != null) {
        render(document.getElementById("loginAlert")) {
            child(AppAlertLogin)
        }
    }
    if (document.getElementById("registerAlert") != null) {
        render(document.getElementById("registerAlert")) {
            child(AppAlertRegister)
        }
    }
}