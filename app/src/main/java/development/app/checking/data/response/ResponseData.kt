package development.app.checking.data.response

import com.google.gson.annotations.SerializedName
import development.app.checking.model.AndroidVersion

open class ResponseData{

    @SerializedName("versions")
    open var  versions: List<AndroidVersion> = listOf()
}