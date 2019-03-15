package development.app.checking.data.repository.remote.response

import com.google.gson.annotations.SerializedName
import development.app.checking.model.AndroidVersion

open class Meta{

    var  status: Boolean = false
    lateinit var  message: String
}