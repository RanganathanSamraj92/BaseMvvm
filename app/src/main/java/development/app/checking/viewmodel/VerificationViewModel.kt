package development.app.checking.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.data.source.remote.NetworkUtils.Companion.updateIdToken
import development.app.checking.model.OTPVerifyResult
import development.app.checking.model.PhoneSignInResult
import development.app.checking.model.SendOTPModel
import development.app.checking.model.UpdateIDToken
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VerificationViewModel : BaseViewModel() {


    @Inject
    lateinit var authApiCall: AuthApiCallInterface



    val updateIdTokenResult = MutableLiveData<UpdateIDToken>()

    val userAvailability = MutableLiveData<OTPVerifyResult>()

    val onVerify = MutableLiveData<PhoneAuthCredential>()

    val phoneSignInResult = MutableLiveData<PhoneSignInResult>()

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var storedVerificationId = MutableLiveData<String>()

    init {
        auth.signOut()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.w("Verify", "onVerificationCompleted:${credential.smsCode}")
                onVerify.postValue(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Verify", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.w("Verify", "Invalid request", e)
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.w("Verify", "The SMS quota for the project has been exceeded", e)
                }

            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.w("Verify", "onCodeSent:" + verificationId!!)


//                // Save verification ID and resending token so we can use them later
                storedVerificationId.postValue(verificationId)
//                resendToken = token

                // ...
            }
        }
    }


    internal fun sendCode(activity: Activity, phoneNumber: String) {

        val firebaseAuthSettings = auth.firebaseAuthSettings

        // Configure faking the auto-retrieval with the whitelisted numbers.
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, "123456")

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$phoneNumber",      // Phone number to verify
            60,               // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            activity,             // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks


    }

    internal fun verifyVerificationCode(activity: Activity, code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(storedVerificationId.value!!, code)
        Log.w("Verify ", "verifying..")
        //signing the user
        signInWithPhoneAuthCredential(activity, credential)
    }


    private lateinit var idToken: String

    internal fun signInWithPhoneAuthCredential(activity: Activity, credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w("Verify SignIn", "signInWithCredential:success")

                    val user = task.result?.user
                    Log.w("Verify SignIn", "user: ${user!!.uid} phoneNumber : ${user.phoneNumber} ")
                    user.getIdToken(true).addOnSuccessListener { tokenResult ->
                        idToken = tokenResult.token.toString()
                        Log.w("idToken", idToken)
                        val phoneSignInResult = PhoneSignInResult()
                        phoneSignInResult.token = idToken
                        phoneSignInResult.uid = user.uid
                        phoneSignInResult.status = true
                        this.phoneSignInResult.postValue(phoneSignInResult)
                    }
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Verify SignIn", "signInWithCredential:failure", task.exception)
                    val phoneSignInResult = PhoneSignInResult()
                    phoneSignInResult.msg = "Sign in failed"
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Log.w("Verify SignIn", "verification code entered was invalid")
                        phoneSignInResult.msg = "verification code entered was invalid"
                    }
                    phoneSignInResult.status = false
                    this.phoneSignInResult.postValue(phoneSignInResult)
                }
            }
    }


    public fun saveToken(idToken: String, uid: String) {

        database.getReference("users/$uid").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Log.w("Verify SignIn", "onDataChange exists")
                    val otpVerifyResult = OTPVerifyResult()
                    otpVerifyResult.availableStatus = true
                    /*updated latest IdToken to DB*/
                    val registerRequest = RegisterRequest()
                    registerRequest.fcmToken = fcmToken!!
                    registerRequest.idToken = idToken
                    otpVerifyResult.registerRequest = registerRequest
                    userAvailability.postValue(otpVerifyResult)
                } else {
                    Log.w("Verify SignIn", "Not exists")
                    val otpVerifyResult = OTPVerifyResult()
                    otpVerifyResult.availableStatus = false
                    val registerRequest = RegisterRequest()
                    otpVerifyResult.registerRequest = registerRequest
                    registerRequest.fcmToken = fcmToken!!
                    registerRequest.phoneNumber = auth.currentUser!!.phoneNumber!!
                    registerRequest.uid = uid
                    registerRequest.idToken = idToken
                    otpVerifyResult.registerRequest = registerRequest
                    userAvailability.postValue(otpVerifyResult)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

    }

    internal fun updateIdToken(idToken:String,uid:String){
        val updateIdToken = UpdateIDToken()
        try {
            databaseRef.child("users/$uid")
                .child("authIdToken")
                .setValue(idToken).addOnSuccessListener {
                    databaseRef.child("users/$uid")
                        .child("fcmToken")
                        .setValue(fcmToken)
                    loadingStatus.value = false
                    updateIdToken.status =true
                    updateIdTokenResult.postValue(updateIdToken)
                }
        } catch (e: Exception) {
            loadingStatus.value = false
            Log.w("upload Exception : ", e.localizedMessage)

            updateIdToken.status =false
            updateIdTokenResult.postValue(updateIdToken)
        }
    }

    internal fun checkQuery(){
        val updateIdToken = UpdateIDToken()
        try {
           val userdRef= databaseRef.child("users")

        } catch (e: Exception) {
            loadingStatus.value = false
            Log.w("upload Exception : ", e.localizedMessage)

            updateIdToken.status =false
            updateIdTokenResult.postValue(updateIdToken)
        }
    }


}
