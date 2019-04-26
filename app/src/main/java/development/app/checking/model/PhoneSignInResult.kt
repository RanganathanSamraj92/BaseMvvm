package development.app.checking.model

import java.io.Serializable

class PhoneSignInResult:Serializable{
    var token  :String = ""
    var msg  :String = ""
    var uid  :String = ""
    var status  :Boolean = false
}