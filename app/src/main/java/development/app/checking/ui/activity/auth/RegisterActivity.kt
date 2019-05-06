package development.app.checking.ui.activity.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.simplepass.loadingbutton.animatedDrawables.ProgressType
import development.app.checking.R
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.pref.PrefMgr
import development.app.checking.ui.activity.HomeActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_register.*


class RegisterActivity : BaseActivity() {

    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_register)
        setAppBarCollapse(resources.getString(R.string.app_name))
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        viewModelSetup(this, registerViewModel)

        inject()
        showProgress = false
        showApiErrorMsg = false

        registerViewModel.errorStatus.observe(this, Observer { error ->
            btnSignUp.revertAnimation()
            showMsg(btnSignUp, error)
        })

        registerViewModel.loginResult.observe(this, Observer { login ->
            btnSignUp.revertAnimation()
            showMsg(btnSignUp, login.message)

            registerViewModel.updateIdTokenResult.observe(this@RegisterActivity, Observer {
                if (it) {
                    prefs.putData(PrefMgr.KEY_ACCESS_TOKEN, login.token)
                    newIntent(this@RegisterActivity, HomeActivity::class.java, "")
                    finish()
                } else {
                    registerViewModel.signOut()
                    showMsg(btnSignUp, "Retry!")
                }
            })
            registerViewModel.updateLoginIdToken(login.token, login.uid)

        })

        btnSignUp.setOnClickListener {
            btnSignUp.progressType = ProgressType.INDETERMINATE
            btnSignUp.startAnimation()
            registerViewModel.register(
                mTxtName.text.toString(),
                mTxtEmail.text.toString(),
                mTxtPassword.text.toString(),
                "",
                mTxtMobile.text.toString()
            )
        }
        txtSignIn.setOnClickListener {
            newIntent(this@RegisterActivity, LoginActivity::class.java, "")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        btnSignUp.dispose()
    }


}
