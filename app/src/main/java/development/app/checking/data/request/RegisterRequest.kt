package development.app.checking.data.request

import java.io.Serializable

class RegisterRequest:Serializable{
    lateinit var uid:String
    lateinit var name:String
    lateinit var email:String
    lateinit var password:String
    lateinit var photoURL:String
    lateinit var phoneNumber:String
    lateinit var fcmToken:String
    lateinit var idToken:String
}