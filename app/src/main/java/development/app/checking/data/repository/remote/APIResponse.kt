package development.app.checking.data.repository.remote

import android.graphics.drawable.shapes.Shape
import com.google.gson.annotations.SerializedName
import development.app.checking.data.repository.OTPResult
import development.app.checking.data.repository.remote.response.Meta
import development.app.checking.data.repository.remote.response.ResponseData
import development.app.checking.model.AndroidVersion
import java.lang.Error
import java.lang.Exception

open class APIResponse{

    @SerializedName("meta")
    lateinit var  meta: Meta

    @SerializedName("data")
    lateinit var  data: ResponseData


    class Success(val result: Any) : APIResponse()

    class Error(val code: Int,val message: String) : APIResponse()

    class Exception(val error:   Any) : APIResponse()

    class Processing(val state: LoadinState ) : APIResponse()


    enum class LoadinState{
        LOADING,
        HIDE,
        SHOW
    }
}