package development.app.checking.data.repository

import android.view.View
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.ApiCallInterface
import development.app.checking.data.source.remote.LoadingState
import development.app.checking.model.AndroidVersion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class VersionsRepository constructor(private val apiService: ApiCallInterface) : BaseRepository() {



    fun getVersions(view : View, apiResponse: MutableLiveData<APIResponse>) {

        apiResponse.postValue(APIResponse.Processing(LoadingState.LOADING))

        GlobalScope.launch {
            try {
                val request = apiService.getVersionsAsync()

                //val response  = request.await()
                val response  = safeApiCall(view, call = {apiService.getVersions().await()})
/*

                if (!response!!.meta.status) {
                    apiResponse.postValue(APIResponse.Success(response.data.versions))
                } else {
                    apiResponse.postValue(APIResponse.Error( response.meta.message))
                }
*/

            } catch (e: Throwable) {
                if (e is IOException) {
                    apiResponse.postValue(APIResponse.Exception("HttpException ${e.message!!}"))
                } else if (e is SocketTimeoutException) {
                    apiResponse.postValue(APIResponse.Exception("SocketTimeoutException ${e.message!!}"))
                }else
                    apiResponse.postValue(APIResponse.Exception("Exception ${e.message!!}"))
            }
        }


    }


    suspend fun getOSVersions(view :View) : MutableLiveData<APIResponse> {
        val response  = safeApiCall( view,call = {apiService.getVersions().await()})
        return response as MutableLiveData<APIResponse>

        //apiResponse.postValue(APIResponse.Processing(LoadingState.LOADING))




    }

    suspend fun getAndroidVerions(view :View) : MutableList<AndroidVersion>?{

        val movieResponse = safeApiCall( view,call = {apiService.getVersions().await()})

        return movieResponse!!.data.versions.toMutableList()

    }


}