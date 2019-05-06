package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    val updateFCMResult = MutableLiveData<Boolean>()


    init {
        signOut()
    }

    internal fun signOut() {
        auth.signOut()
    }

    private lateinit var idToken: String


    fun signIn(email: String, password: String) {
        loadingStatus.value = true
        scope.launch {
            var user = auth.currentUser
            if (user != null) {
                user.getIdToken(true).addOnSuccessListener { tokenResult ->
                    idToken = tokenResult.token.toString()
                    Log.w("idToken", idToken)
                    saveToken(idToken, user.uid)
                }

            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Log.e("user", user.toString())
                        user!!.getIdToken(true).addOnSuccessListener { tokenResult ->
                            idToken = tokenResult.token.toString()
                            Log.w("idToken", idToken)
                            saveToken(idToken, user.uid)

                        }
                    } else {
                        Log.w("user", it.exception!!.localizedMessage)
                        errorStatus.value = it.exception!!.localizedMessage
                    }

                }
            }


        }
    }

    private fun saveToken(idToken: String, uid: String) {
        val loginModel = LoginModel()
        loginModel.message = "login successful!"
        loginModel.token = idToken
        loginModel.uid = uid
        loginResult.postValue(loginModel)
        updateLoginIdToken(idToken, uid)

    }


    internal fun updateLoginIdToken(token: String, userId: String) {
        scope.launch {
            //uploadingStatus.postValue(true)
            val datebaseRef = database.reference
            try {
                var authIdTokenReference =
                    datebaseRef.child("users/$userId/authIdToken").setValue(token).addOnSuccessListener {
                        updateFCMToken(fcmToken!!, userId)
                    }
            } catch (e: Exception) {
                //uploadingStatus.postValue(false)
                Log.w("upload Exception : ", e.localizedMessage)
            }
        }
    }

    internal fun updateFCMToken(token: String, userId: String) {
        scope.launch {
            //uploadingStatus.postValue(true)
            val datebaseRef = database.reference
            try {
                var fcmTokenReference =
                    datebaseRef.child("users/$userId/fcmToken").setValue(token).addOnSuccessListener {
                        updateFCMResult.postValue(true)
                    }
            } catch (e: Exception) {
                //uploadingStatus.postValue(false)
                updateFCMResult.postValue(false)
                Log.w("upload Exception : ", e.localizedMessage)
            }
        }

    }
}
