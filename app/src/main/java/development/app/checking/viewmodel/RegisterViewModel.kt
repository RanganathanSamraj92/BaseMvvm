package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val loginResult = MutableLiveData<LoginModel>()


    init {

    }

    fun register(name: String,email:String,password:String,image:String,mobile:String) {
        loadingStatus.value = true
        scope.launch {
            val registerRequest = RegisterRequest()
            registerRequest.name = name
            registerRequest.email = email
            registerRequest.password = password
            registerRequest.photoURL = "https://seeklogo.net/wp-content/uploads/2015/09/Google_2015_logo1.svg"
            registerRequest.phoneNumber = mobile
            val apiResponse = repository.register(registerRequest)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    loginResult.postValue(res.data.loginModel)
                }
            } catch (e: Throwable) {

            }

        }
    }
}
