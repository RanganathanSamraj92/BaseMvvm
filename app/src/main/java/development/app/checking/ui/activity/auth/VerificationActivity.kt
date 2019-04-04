package development.app.checking.ui.activity.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_verification.*


class VerificationActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.content_verification)
        setAppBar(resources.getString(R.string.app_name))
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModelSetup(this, loginViewModel)



        loginViewModel.loginResult.observe(this, Observer { login ->
            toolbar_layout.title = login.message


        })

        btnVerify.setOnClickListener {
        }


    }


}
