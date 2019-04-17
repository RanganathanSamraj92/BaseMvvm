package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.VerifyTokenModel
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

    private lateinit var idToken: String

    fun register(name: String, email:String, password:String, image:String, mobile:String) {
        loadingStatus.value = true
        scope.launch {
            val registerRequest = RegisterRequest()
            registerRequest.name = name
            registerRequest.email = email
            registerRequest.password = password
            registerRequest.photoURL = "https://seeklogo.net/wp-content/uploads/2015/09/Google_2015_logo1.svg"
            registerRequest.phoneNumber = mobile
            registerRequest.fcmToken = fcmToken!!
            val apiResponse = repository.register(registerRequest)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            Log.e("user", user.toString())
                            user!!.getIdToken(true).addOnSuccessListener { tokenResult ->
                                idToken = tokenResult.token.toString()
                                Log.w("idToken",idToken)
                                login(idToken)

                            }
                        } else {
                            Log.w("user", it.exception!!.localizedMessage)
                            errorStatus.value = it.exception!!.localizedMessage
                        }

                    }
                }
            } catch (e: Throwable) {

            }

        }
    }

    private fun login(token: String) {
        var verifyTokenModel = VerifyTokenModel()
        verifyTokenModel.idToken = token
        scope.launch {
            val apiResponse = repository.verifyToken(verifyTokenModel)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    loginResult.postValue(res.data.loginModel)

                }
            } catch (e: Throwable) {
                Log.e("verifyIdToken Error", e.localizedMessage)
            }
        }
    }

}
