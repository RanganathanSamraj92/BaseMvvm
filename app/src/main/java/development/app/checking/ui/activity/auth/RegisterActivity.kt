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
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_register.*


class RegisterActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_register)
        setAppBarCollapse(resources.getString(R.string.app_name))
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModelSetup(this, loginViewModel)



        loginViewModel.loginResult.observe(this, Observer { login ->
            if (login.auth) {
                txtContent.text = login.token
                toolbar_layout.title = login.message
            }else{
                showAlert("Authentication Failed",login.message,object:Utils.OnClickListener{
                    override fun onClick(v: View) {

                    }

                },object:Utils.OnClickListener{
                    override fun onClick(v: View) {

                    }
                })
                txtContent.text = login.token
                toolbar_layout.title = login.message
            }


        })

        btnSignUp.setOnClickListener {
            loginViewModel.login(mTxtEmail.text.toString(), mTxtPassword.text.toString())
        }
        txtSignIn.setOnClickListener {
            newIntent(this@RegisterActivity, LoginActivity::class.java,"")
        }



    }





}
