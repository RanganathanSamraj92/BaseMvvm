package development.app.checking.model

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

class ProfileModel {
    var name: String = ""
    val email: String = ""
    @get:PropertyName("photoUrl")
    @set:PropertyName("photoUrl")
    var image: String = ""
    val mobile: String = ""


}

