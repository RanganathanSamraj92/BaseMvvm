package development.app.checking.model

import java.io.Serializable

class UpdateIDToken : Serializable {
    var token: String = ""
    var status: Boolean = false
}