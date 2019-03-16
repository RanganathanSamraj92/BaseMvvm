package development.app.checking.data.repository

import development.app.checking.data.source.remote.APIResponse
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.UnknownHostException

@Suppress("UNCHECKED_CAST")
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {
        var data: T? = null
        val response = safeApiResult(call)
        return response as T
    }

}

private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResponse {

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
