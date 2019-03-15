package development.app.checking.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class VersionViewModel : BaseViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : VersionsRepository =
        VersionsRepository(RetrofitFactory.makeRetrofitService())

    val response = MutableLiveData<APIResponse>()
    private val androidVersions =  MutableLiveData<MutableList<AndroidVersion>>()

    open fun getAndroidVerions(): MutableLiveData<MutableList<AndroidVersion>> {
        return androidVersions
    }

    fun fetchMovies(view : View){
        scope.launch {
         // repository.getVersions(response)
         //   response.postValue( repository.getOSVersions())
          androidVersions.postValue(repository.getAndroidVerions(view))
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}

