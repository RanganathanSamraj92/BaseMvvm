package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.model.AppVersion
import development.app.checking.model.VersionDetail
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel() {

    private val repository: VersionsRepository = VersionsRepository(RetrofitFactory.makeRetrofitService())

    val versionDetail = MutableLiveData<VersionDetail>()


    fun fetchDetails() {
        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.getAppVersionDetail()
            val res= handleResponses(apiResponse!!)
            try {
                versionDetail.postValue(res.data.versionDetails)
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }
    }
}
