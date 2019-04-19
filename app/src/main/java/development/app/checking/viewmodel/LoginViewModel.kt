package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.model.LoginModel
import development.app.checking.model.UpdateFCMModel
import development.app.checking.model.VerifyTokenModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {




    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val loginResult = MutableLiveData<LoginModel>()

    val updateFCMResult = MutableLiveData<LoginModel>()




    init {


    }

    private lateinit var idToken: String

    fun signIn(email: String, password: String) {
        loadingStatus.value = true
        scope.launch {
            var user = auth.currentUser
            if (user != null) {
                user.getIdToken(true).addOnSuccessListener { tokenResult ->
                    idToken = tokenResult.token.toString()
                    Log.w("idToken",idToken)
                    login(idToken)
                }

            } else {
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

    internal fun updateFCMTokenOnDB() {
        scope.launch {
           val updateFCMModel = UpdateFCMModel()
            updateFCMModel.fcmToken =fcmToken!!
            updateFCMModel.idToken =idToken
            val apiResponse = repository.updateFCMToken(updateFCMModel)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    updateFCMResult.postValue(res.data.loginModel)
                }
            } catch (e: Throwable) {
                Log.e("updateFCM Error", e.localizedMessage)
            }
        }
    }
}
