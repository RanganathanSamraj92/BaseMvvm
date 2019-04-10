package development.app.checking.data.source.remote

import android.util.Log
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

open class RetrofitFactory {


    companion object {
        val LOCAL_BASE_URL1 = "https://baseapis.herokuapp.com/"


        suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResponse {

            try {
                val response = call.invoke()
                Log.w("API details :","\nstatuscode: ${response.code()}" +
                        ",\n" +
                        "success : ${response.isSuccessful} " +
                        ",\n" +
                        "Body ${response.raw().networkResponse()}" +

                        ",\n" +
                        "message ${response.message()}" +
                        ",\n" +
                        "errorBody ${response.errorBody()}" +
                        " \n" +
                        "Headers ${response.headers()} ")

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