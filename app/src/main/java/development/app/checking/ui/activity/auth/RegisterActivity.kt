package development.app.checking.ui.activity.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.pref.PrefMgr.Companion.KEY_TOKEN
import development.app.checking.ui.activity.HomeActivity
import development.app.checking.ui.activity.SplashActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.LoginViewModel
import development.app.checking.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
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


        registerViewModel.loginResult.observe(this, Observer { login ->
            showMsg(btnSignUp, login.message)
            prefs.putData(KEY_TOKEN,login.token)
            newIntent(this@RegisterActivity, HomeActivity::class.java,"")


        })

        btnSignUp.setOnClickListener {
            registerViewModel.register(
                mTxtName.text.toString(),
                mTxtEmail.text.toString(),
                mTxtPassword.text.toString(),
                "http://www.developersacademy.org/blog/wp-content/uploads/2018/03/Android-Developer-1024x682.jpg",
                mTxtMobile.text.toString())
        }
        txtSignIn.setOnClickListener {
            newIntent(this@RegisterActivity, LoginActivity::class.java,"")
        }



    }





}
