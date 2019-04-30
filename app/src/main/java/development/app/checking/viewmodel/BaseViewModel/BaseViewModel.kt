package development.app.checking.viewmodel.BaseViewModel

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import development.app.checking.app.App
import development.app.checking.data.response.base.Meta
import development.app.checking.data.source.remote.*
import development.app.checking.di.injectors.DaggerViewModelInjector
import development.app.checking.di.injectors.ViewModelInjector
import development.app.checking.di.modules.LocalNetworkModule
import development.app.checking.di.modules.NetworkModule
import development.app.checking.di.modules.SharedPreferencesModule
import development.app.checking.viewmodel.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {


    internal  var TAG_FIRESTORE: String = "firestore"

    internal lateinit var auth: FirebaseAuth
    internal lateinit var database: FirebaseDatabase
    internal lateinit var databaseRef: DatabaseReference
    internal lateinit var firestoreDB: FirebaseFirestore

    internal var fcmToken: String? = null

    private val injector : ViewModelInjector = DaggerViewModelInjector.builder()
    .networkModule(networkModule = NetworkModule)
    .localNetworkModule(localNetworkModule = LocalNetworkModule)
    .build()

    init {

        inject()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseRef = database.reference
        // Access a Cloud Firestore instance from your Activity
        firestoreDB = FirebaseFirestore.getInstance()


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("messaging", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                fcmToken = task.result?.token

                // Log and toast
                val msg = fcmToken
                Log.w("messaging Token", msg)
            })
    }


    fun inject() {
        when (this) {
            is SplashViewModel -> injector.inject(this)

            is VersionViewModel -> injector.inject(this)

            is DetailViewModel -> injector.inject(this)

            is LoginViewModel -> injector.inject(this)

            is ProfileViewModel -> injector.inject(this)

            is RegisterViewModel  -> injector.inject(this)

            is ImageUploadViewModel  -> injector.inject(this)

            is ForgotPasswordViewModel  -> injector.inject(this)

            is VerificationViewModel  -> injector.inject(this)

            is ResetPasswordViewModel  -> injector.inject(this)
        }

    }

    open var baseApiResponse: MutableLiveData<APIResponse> = MutableLiveData()

    open var loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    open var metaStatus: MutableLiveData<Meta> = MutableLiveData()
    open var errorStatus: MutableLiveData<String> = MutableLiveData()

    private val parentJob by lazy { Job() }
    private val scopeJob by lazy { Job() }
    val coroutineContext: CoroutineContext by lazy { Dispatchers.Main + parentJob }



    /*private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO*/

    val scope = GlobalScope


    open fun cancelAllRequests() {
        coroutineContext.cancel()
        parentJob.cancel()
    }

   public override fun onCleared() {
       cancelAllRequests()
        super.onCleared()
        Log.w("TAG", "OnCleared")


    }

    open fun handleResponses(apiResponse: APIResponse): APIResponse {

        when (apiResponse) {

            is APIResponse.Success -> {
                loadingStatus.postValue(false)
                apiResponse.successResult as APIResponse?
                return if (apiResponse.successResult.meta.status) {
                    metaStatus.postValue(apiResponse.successResult.meta)
                    apiResponse.successResult
                } else {
                    metaStatus.postValue(apiResponse.successResult.meta)
                    apiResponse.successResult
                }
            }

            is APIResponse.Error -> {
                loadingStatus.postValue(false)
                errorStatus.postValue(apiResponse.errorMessage as String)
            }

            is APIResponse.Exception -> {
                loadingStatus.postValue(false)
                errorStatus.postValue(apiResponse.exception as String)
            }


        }
        return apiResponse
    }
}