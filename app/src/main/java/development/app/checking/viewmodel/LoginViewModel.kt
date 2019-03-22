package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.LoginRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginResult
import development.app.checking.pref.Prefs
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface



    private val repository = AuthRepository(authApiCall)

    val loginResult = MutableLiveData<LoginResult>()



    init {

    }

    fun login( email:String,password:String) {
        loadingStatus.value = true
       /* if (!prefs.getData("token","").equals("")) {
            scope.launch {
                var  loginRequest = LoginRequest()
                loginRequest.email = email
                loginRequest.password = password
                val apiResponse = repository.login(loginRequest)
                val res = handleResponses(apiResponse!!)
                try {
                    if (res.meta.status) {
                        loginResult.postValue(res.data.result)
                        prefs.putData("token",res.data.result.token)

                    }
                } catch (e: Throwable) {

                }

            }
        }else{
           Log.w("LOG","Already logged In")
        }*/


    }
}
