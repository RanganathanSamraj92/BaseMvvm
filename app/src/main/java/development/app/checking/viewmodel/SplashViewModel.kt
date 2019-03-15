package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.model.AppVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

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
        GlobalScope.launch {
            delay(2000)
            launch(Dispatchers.Main) {
                var version = AppVersion()
                version.version = "1.0.26"
                appVersion.postValue(version)
            }

        }
    }
}
