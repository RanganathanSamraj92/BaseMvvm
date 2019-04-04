package development.app.checking.data.source.remote

import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

open class RetrofitFactory {


    companion object {
        val LOCAL_BASE_URL1 = "http://192.168.43.119:3000/"


        suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResponse {

            try {
                val response = call.invoke()
                if (response.isSuccessful)
                    return APIResponse.Success(response.body()!!)
                return APIResponse.Error("Api Error ${response.code()} ${response.errorBody()}")
            } catch (e: UnknownHostException) {
                return APIResponse.Exception("No Internet connection Exception : ${e.localizedMessage}")
            } catch (e: IOException) {
                return APIResponse.Exception("safeApiResult IOException : ${e.localizedMessage}")
            } catch (e: Exception) {
                return APIResponse.Exception("safeApiResult Exception : ${e.localizedMessage}")
            }
        }


    }


}