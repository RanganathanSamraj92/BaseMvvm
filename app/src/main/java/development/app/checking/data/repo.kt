package development.app.checking.data

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.BaseRepository
import development.app.checking.data.repository.remote.APIResponse
import development.app.checking.data.repository.remote.ApiCallInterface
import development.app.checking.model.AndroidVersion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class VersionsRepository constructor(private val apiService: ApiCallInterface) : BaseRepository() {



    fun getVersions(apiResponse: MutableLiveData<APIResponse>) {

        apiResponse.postValue(APIResponse.Processing(APIResponse.LoadingState.LOADING))

        GlobalScope.launch {
            try {
                val request = apiService.getVersionsAsync()

                //val response  = request.await()
                val response  = safeApiCall( call = {apiService.getVersions().await()})

                if (response!!.meta.status) {
                    apiResponse.postValue(APIResponse.Success(response.data.results))
                } else {
                    apiResponse.postValue(APIResponse.Error(400, response.meta.message))
                }

            } catch (e: Throwable) {
                if (e is IOException) {
                    apiResponse.postValue(APIResponse.Exception("HttpException ${e.message!!}"))
                } else if (e is SocketTimeoutException) {
                    apiResponse.postValue(APIResponse.Exception("SocketTimeoutException ${e.message!!}"))
                }
            }
        }


    }


    suspend fun getAndroidVerions() : MutableList<AndroidVersion>?{

        val movieResponse = safeApiCall( call = {apiService.getVersions().await()})

        return movieResponse!!.data.results.toMutableList()

    }


}