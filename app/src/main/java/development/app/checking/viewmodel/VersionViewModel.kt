package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.ApiCallInterface
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class VersionViewModel : BaseViewModel() {

    @Inject
    lateinit var verionsApi: ApiCallInterface

    private val repository: VersionsRepository = VersionsRepository(verionsApi)

    val androidVersions = MutableLiveData<MutableList<AndroidVersion>>()


    init {
        fetchVersions()
    }

    fun fetchVersions() {
        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.getOSVersions()
            baseApiResponse.postValue(apiResponse)
            val res = handleResponses(apiResponse!!)
            try {
                androidVersions.postValue(res.data.versions.toMutableList())
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }
    }
}

