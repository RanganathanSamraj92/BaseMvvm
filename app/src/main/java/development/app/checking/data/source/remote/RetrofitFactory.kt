package development.app.checking.data.source.remote

import android.util.Log
import com.squareup.moshi.Json
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

open class RetrofitFactory {


    companion object {
        val LOCAL_BASE_URL1 = "https://us-central1-baseapi-e2980.cloudfunctions.net/api/v1/"


        suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResponse {

            try {
                val response = call.invoke()
                val k = response.body() as APIResponse
                Log.w("API details :","\nstatuscode: ${response.code()}" +
                        ",\n" +
                        "success : ${response.isSuccessful} " +
                        ",\n" +
                        "Body ${response.raw().networkResponse()}" +

                        ",\n" +
                        "content body - status : ${k.meta.status}  msg : ${k.meta.message}" +
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