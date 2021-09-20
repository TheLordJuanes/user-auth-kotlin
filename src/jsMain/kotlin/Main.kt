import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    render(document.getElementById("root")) {
        render(document.getElementById("users")) {
            child(App)
        }
    }
}