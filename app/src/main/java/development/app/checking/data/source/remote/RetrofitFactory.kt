package development.app.checking.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.UnknownHostException

open class RetrofitFactory {


    val BASE_URL = "https://jsonplaceholder.typicode.com"

    companion object {
        val BASE_URL1 = "http://shamlatech.net/android_portal/kotlinApps/api/v1/"
        val LOCAL_BASE_URL1 = "http://localhost:3000/auth/"
        fun makeRetrofitService(): ApiCallInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                /*.addConverterFactory(MoshiConverterFactory.create())*/
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(ApiCallInterface::class.java)
        }

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