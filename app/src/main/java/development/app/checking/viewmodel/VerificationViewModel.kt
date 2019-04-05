package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.LoginRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.SendOTPModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class VerificationViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val sendOTPResult = MutableLiveData<SendOTPModel>()


    init {

    }

    fun resendOTP(mobileOrEmail: String) {
        loadingStatus.value = true
        scope.launch {
            val apiResponse = repository.sendOTP(mobileOrEmail)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    sendOTPResult.postValue(res.data.sendOTPModel)

                }
            } catch (e: Throwable) {

            }

        }
    }
}
