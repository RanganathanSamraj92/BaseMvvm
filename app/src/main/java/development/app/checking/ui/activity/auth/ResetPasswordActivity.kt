package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.ResetPasswordViewModel
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_reset_password.*


class ResetPasswordActivity : BaseActivity() {

    private lateinit var resetPasswordViewModel: ResetPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.content_reset_password)
        setAppBar(resources.getString(R.string.title_activity_reset_password))
        resetPasswordViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        viewModelSetup(this, resetPasswordViewModel)


        resetPasswordViewModel.errorStatus.observe(this, Observer {
            newIntent(context,LoginActivity::class.java,"")
            finish()
        })

        resetPasswordViewModel.metaStatus.observe(this, Observer { msg ->
            toolbar.title = msg
        })

        btnResetPassword.setOnClickListener {
            if (txtPassword.text.toString().isEmpty()){
                showMsg(txtPassword,showMsgCantEmpty("Password "))
                return@setOnClickListener
            }
            if (txtConfirmPassword.text.toString().isEmpty()){
                showMsg(txtPassword,showMsgCantEmpty("Confirm Password "))
                return@setOnClickListener
            }
            if (txtConfirmPassword.text.toString()==txtPassword.text.toString()) {
                resetPasswordViewModel.resetPassword("9988776655")
            }else{
                showMsg(txtPassword,"Password mismatched!")
            }




        }


    }


}
