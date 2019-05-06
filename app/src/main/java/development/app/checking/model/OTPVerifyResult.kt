package development.app.checking.model

import development.app.checking.data.request.RegisterRequest
import java.io.Serializable

class OTPVerifyResult : Serializable {

    var availableStatus = false

    lateinit var registerRequest: RegisterRequest


}