package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.BuildConfig
import development.app.checking.model.AppVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel

class AppInfoViewModel : BaseViewModel() {



    private val appVersion: MutableLiveData<AppVersion> = MutableLiveData()

    val version = MutableLiveData<String>()
    init {
        version.value = "version : ${BuildConfig.VERSION_NAME}"

    }


    fun getAppVersion(): LiveData<AppVersion> {
        return appVersion
    }





}
