package development.app.checking.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.OTPResult
import development.app.checking.data.repository.remote.APIResponse
import development.app.checking.data.repository.remote.ApiCallInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VersionsRepository  constructor(private val apiService: ApiCallInterface) {

    fun getVersions(reult: MutableLiveData<APIResponse>) {


        reult.postValue(APIResponse.Processing(APIResponse.LoadinState.LOADING))

        GlobalScope.launch  {
            try {
                val request = apiService.getVersionsAsync()
                val response = request.await()
                Log.w("AB",response.toString())

                val k =  6/0
                if (response.meta.status) {
                    reult.postValue(APIResponse.Success(response.data.results))
                } else {
                    reult.postValue(APIResponse.Error(400,response.meta.message))
                }

            } catch (exception: Exception) {
                reult.postValue(APIResponse.Exception(exception.message!!))

            }
        }


    }








}