package development.app.checking.data.repository.remote

import com.google.gson.annotations.SerializedName
import development.app.checking.data.repository.remote.response.Meta
import development.app.checking.data.repository.remote.response.ResponseData

open class APIResponse{

    @SerializedName("meta")
    lateinit var  meta: Meta

    @SerializedName("data")
    lateinit var  data: ResponseData


    class Success(val result: Any) : APIResponse()

    class Error(val code: Int,val errorMessage: Any) : APIResponse()

    class Exception(val error:   Any) : APIResponse()

    class Processing(val state: LoadingState ) : APIResponse()


    enum class LoadingState{
        LOADING,
        HIDE,
        SHOW
    }
}