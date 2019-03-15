package development.app.checking.data.source.remote

import com.google.gson.annotations.SerializedName
import development.app.checking.data.response.Meta
import development.app.checking.data.response.ResponseData
import development.app.checking.model.AndroidVersion

open class APIResponse {

     @SerializedName("meta")
     lateinit var  meta: Meta

     @SerializedName("data")
     var  data: ResponseData = ResponseData()


    open class Success(val result: Any) : APIResponse()

    class Error(val errorMessage: Any) : APIResponse()

    class Exception(val error: Any) : APIResponse()

    class Processing(val state: LoadingState) : APIResponse()


}

enum class LoadingState {
    LOADING,
    HIDE,
    SHOW
}