package development.app.checking.data.repository

import android.util.Log
import android.view.View
import development.app.checking.data.response.Meta
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.ui.base.BaseActivity.Companion.showMsg
import kotlinx.android.synthetic.main.content_android_versions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(view: View, call: suspend () -> Response<T>): T? {
        var data: T? = null
        val result: APIResponse = safeApiResult(call)


        when (result) {
            is APIResponse.Success -> {
                data = result.result as T

                Log.d("1.DataRepository", " & Exception - ${result}")
            }


            is APIResponse.Error -> {
                data = result.errorMessage as T
                Log.d("1.DataRepository", " & Exception - ${result.errorMessage}")
            }

            is APIResponse.Exception -> {
                data = result.error as T
                Log.d("1.DataRepository", " & Exception - ${result.error}")
            }

        }


        return data
    }

}

private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResponse {
    val response = call.invoke()
    if (response.isSuccessful)
        return APIResponse.Success(response.body()!!)

    return APIResponse.Error( response.code() )
}
