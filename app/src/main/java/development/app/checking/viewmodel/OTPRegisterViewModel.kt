package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.model.LoginModel
import development.app.checking.model.UpdateIDToken
import development.app.checking.model.VerifyTokenModel
import development.app.checking.pref.PrefMgr
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class OTPRegisterViewModel : BaseViewModel() {


    val updateIdTokenResult = MutableLiveData<UpdateIDToken>()


    fun saveUser(request: RegisterRequest) {
        loadingStatus.value = true
        scope.launch {
            try {
                var authIdTokenReference = databaseRef.child("users/${request.uid}").setValue(request).addOnSuccessListener {
                    val updateIdToken = UpdateIDToken()
                    updateIdToken.status =true
                    updateIdToken.token =request.idToken
                    updateIdTokenResult.postValue(updateIdToken)
                    loadingStatus.value = false
                }
            } catch (e: Exception) {
                loadingStatus.value = false
                Log.w("upload Exception : ", e.localizedMessage)
                val updateIdToken = UpdateIDToken()
                updateIdToken.status =false
                updateIdTokenResult.postValue(updateIdToken)

            }

        }
    }




}
