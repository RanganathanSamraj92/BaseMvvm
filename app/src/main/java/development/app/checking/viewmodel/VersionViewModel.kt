package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.*

class VersionViewModel : BaseViewModel() {

    private val repository: VersionsRepository = VersionsRepository(RetrofitFactory.makeRetrofitService())

    val androidVersions = MutableLiveData<MutableList<AndroidVersion>>()


    fun fetchVersions() {
        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.getOSVersions()
            baseApiResponse.postValue(apiResponse)
            val res= handleResponses(apiResponse!!)
            try {
                androidVersions.postValue(res.data.versions.toMutableList())
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }
    }
}

