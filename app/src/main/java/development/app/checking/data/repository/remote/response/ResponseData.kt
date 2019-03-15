package development.app.checking.data.repository.remote.response

import com.google.gson.annotations.SerializedName
import development.app.checking.model.AndroidVersion

open class ResponseData{

    @SerializedName("versions")
    open var  results: List<AndroidVersion> = listOf()
}