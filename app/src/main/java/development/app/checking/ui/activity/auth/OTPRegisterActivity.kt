package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.simplepass.loadingbutton.animatedDrawables.ProgressType
import development.app.checking.R
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.pref.PrefMgr
import development.app.checking.ui.activity.HomeActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.OTPRegisterViewModel
import kotlinx.android.synthetic.main.content_register.*


class OTPRegisterActivity : BaseActivity() {

    private lateinit var registerViewModel: OTPRegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_otp_register)
        setAppBarCollapse(resources.getString(R.string.app_name))
        registerViewModel = ViewModelProviders.of(this).get(OTPRegisterViewModel::class.java)
        viewModelSetup(this, registerViewModel)

        inject()

        var registerModel = intent.getSerializableExtra("intent_data") as RegisterRequest

        showProgress = false
        showApiErrorMsg = false



        registerViewModel.updateIdTokenResult.observe(this@OTPRegisterActivity, Observer {
            if (it.status) {
                prefs.putData(PrefMgr.KEY_ACCESS_TOKEN, it.token)
                newIntent(this@OTPRegisterActivity, HomeActivity::class.java, "")
                finish()
            } else {
                btnSignUp.revertAnimation()
                btnSignUp.clearAnimation()
                showMsg(btnSignUp, "Retry!")
            }
        })

        btnSignUp.setOnClickListener {
            registerModel.name = "Ranga"
            registerModel.email = ""
            registerModel.password = ""
            registerModel.photoURL = "https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_28dp.png"
            btnSignUp.progressType = ProgressType.INDETERMINATE
            btnSignUp.startAnimation()
            registerViewModel.saveUser(registerModel)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        btnSignUp.dispose()
    }


}
