package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.VersionsRepository
import development.app.checking.data.repository.remote.APIResponse
import development.app.checking.data.repository.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class VersionViewModel : BaseViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : VersionsRepository = VersionsRepository(RetrofitFactory.makeRetrofitService())

    val response = MutableLiveData<APIResponse>()
    private val androidVersions =  MutableLiveData<MutableList<AndroidVersion>>()

    open fun getAndroidVerions(): MutableLiveData<MutableList<AndroidVersion>> {
        return androidVersions
    }

    fun fetchMovies(){
        scope.launch {
          //repository.getVersions(response)
            androidVersions.postValue(repository.getAndroidVerions())
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}

