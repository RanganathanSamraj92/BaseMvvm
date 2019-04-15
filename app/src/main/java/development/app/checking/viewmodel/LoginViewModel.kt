package development.app.checking.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import development.app.checking.R
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.VerifyTokenModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {


    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val loginResult = MutableLiveData<LoginModel>()


    init {

// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        if (user != null) {
            val vm = LoginModel()
            user.getIdToken(true).addOnSuccessListener { tokenResult ->
                vm.message = tokenResult.token.toString()
                loginResult.postValue(vm).toString()
            }

        } else {
            var vm = LoginModel()
            vm.message = "Logged Out"
            loginResult.postValue(vm)
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("messaging", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg =  token
                Log.w("messaging Token", msg)
            })
    }

    fun login(email: String, password: String) {
        loadingStatus.value = true
        scope.launch {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.e("user", user.toString())
                    user!!.getIdToken(true).addOnSuccessListener { tokenResult ->
                        var token = tokenResult.token.toString()
                        verifyIdTokenOnServer(token)

                    }


                } else {
                    Log.e("user", it.exception!!.localizedMessage)
                    var vm = LoginModel()
                    vm.message = it.exception!!.localizedMessage
                    loginResult.postValue(vm)
                }

            }

        }
    }

    private fun verifyIdTokenOnServer(token: String) {
        var verifyTokenModel = VerifyTokenModel()
        scope.launch {
            verifyTokenModel.token = token
            val apiResponse = repository.verifyToken(verifyTokenModel)
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
