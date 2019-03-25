package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.ProfileModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val userInfo = MutableLiveData<ProfileModel>()


    init {

    }

    fun getProfile(token: String) {
        loadingStatus.value = true
        scope.launch {
            val apiResponse = repository.me(token)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    userInfo.postValue(res.data.userInfo)
                }
            } catch (e: Throwable) {

            }

        }
    }
}
