package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.ApiCallInterface
import development.app.checking.model.VersionDetail
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel : BaseViewModel() {

    @Inject
    lateinit var versionsApi: ApiCallInterface

    private val repository: VersionsRepository = VersionsRepository(versionsApi)

    val versionDetail = MutableLiveData<VersionDetail>()

    init {
        inject()
    }

    fun fetchDetails() {
        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.getAppVersionDetail()
            val res = handleResponses(apiResponse!!)
            try {
                versionDetail.postValue(res.data.versionDetails)
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }
    }
}
