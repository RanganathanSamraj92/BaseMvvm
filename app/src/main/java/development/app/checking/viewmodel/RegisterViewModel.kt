package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.model.LoginModel
import development.app.checking.model.VerifyTokenModel
import development.app.checking.pref.PrefMgr
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface

    internal lateinit var msgRef: DatabaseReference


    private val repository = AuthRepository(authApiCall)

    val loginResult = MutableLiveData<LoginModel>()
    val updateIdTokenResult = MutableLiveData<Boolean>()

    init {
        signOut()
    }

    internal fun signOut() {
        auth.signOut()
    }

    private lateinit var idToken: String

    fun register(name: String, email: String, password: String, image: String, mobile: String) {
        loadingStatus.value = true
        scope.launch {
            val registerRequest = RegisterRequest()
            registerRequest.name = name
            registerRequest.email = email

            registerRequest.password = password
            registerRequest.photoURL = "https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_28dp.png"
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
                                Log.w("idToken", idToken)
                                res.data.loginModel.token = idToken
                                res.data.loginModel.uid = user.uid
                                loginResult.postValue(res.data.loginModel)

                            }
                        } else {
                            Log.w("user", it.exception!!.localizedMessage)
                            errorStatus.value = it.exception!!.localizedMessage
                        }

                    }
                } else {
                    errorStatus.postValue(res.meta.message)
                }
            } catch (e: Throwable) {

            }

        }
    }

    internal fun updateLoginIdToken(token: String, userId: String) {
        scope.launch {
            //uploadingStatus.postValue(true)
            val datebaseRef = database.reference
            try {
                var authIdTokenReference =
                    datebaseRef.child("users/$userId/authIdToken").setValue(token).addOnSuccessListener {
                        updateIdTokenResult.postValue(true)
                    }
            } catch (e: Exception) {
                //uploadingStatus.postValue(false)
                updateIdTokenResult.postValue(false)
                Log.w("upload Exception : ", e.localizedMessage)
            }
        }
    }


}
