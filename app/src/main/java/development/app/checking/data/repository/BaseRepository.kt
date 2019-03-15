package development.app.checking.data.repository

import android.util.Log
import development.app.checking.data.repository.remote.APIResponse
import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {

        val result : APIResponse = safeApiResult(call)
        var data : T? = null

        when(result) {
            is APIResponse.Success ->
                data = result.result as T
            is APIResponse.Error -> {
                data = APIResponse.Error(result.code,result.errorMessage) as T
                Log.d("1.DataRepository", " & Exception - ${result.errorMessage}")
            }
        }


        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : APIResponse{
        val response = call.invoke()
        if(!response.isSuccessful)
            return APIResponse.Success(response.body()!!)

        return APIResponse.Error(404,"${response.code()} ${response.raw().message()} Error Occurred during getting safe Api result, Custom ERROR ")
    }
}