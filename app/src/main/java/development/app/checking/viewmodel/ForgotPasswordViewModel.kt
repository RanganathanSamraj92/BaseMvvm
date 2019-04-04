package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.LoginRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgotPasswordViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)


    init {

    }

    fun authenticate(emailOrMobile: String) {
        loadingStatus.value = true
        scope.launch {

            val apiResponse = repository.authenticate(emailOrMobile)
            val res = handleResponses(apiResponse!!)

        }
    }
}
