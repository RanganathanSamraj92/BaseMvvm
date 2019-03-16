package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AppVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    private val repository: VersionsRepository = VersionsRepository(RetrofitFactory.makeRetrofitService())

    private val appVersion: MutableLiveData<AppVersion> = MutableLiveData()
    open val state: MutableLiveData<String> = MutableLiveData()

    init {
        state.value = "loading"
        loadAppVersion()
    }


    fun getAppVersion(): LiveData<AppVersion> {
        return appVersion
    }

    private fun loadAppVersion() {

        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.getAppVersion()
            val res= handleResponses(apiResponse!!)
            try {
                appVersion.postValue(res.data.appVersion)
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }

    }
}
