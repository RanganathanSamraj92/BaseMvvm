package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.ApiCallInterface
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.AndroidVersion
import development.app.checking.model.LoginModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface

    private val repository = AuthRepository(authApiCall)

    val loginModel = MutableLiveData<LoginModel>()


    init {
    }

    fun login( email:String,password:String) {
        loadingStatus.value = true

        scope.launch {
            val apiResponse = repository.login()
            apiResponse as LoginModel
            loginModel.value = apiResponse

        }
    }
}
