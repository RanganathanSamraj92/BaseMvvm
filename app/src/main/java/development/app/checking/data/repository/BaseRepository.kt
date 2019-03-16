package development.app.checking.data.repository

import development.app.checking.data.source.remote.RetrofitFactory
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {
        var data: T? = null
        val response = RetrofitFactory.safeApiResult(call)
        return response as T
    }

}

