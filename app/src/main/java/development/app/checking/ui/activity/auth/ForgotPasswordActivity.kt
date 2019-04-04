package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_forgot_password.*


class ForgotPasswordActivity : BaseActivity() {

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.content_forgot_password)
        setAppBar(resources.getString(R.string.app_name))
        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        viewModelSetup(this, forgotPasswordViewModel)



        forgotPasswordViewModel.metaStatus.observe(this, Observer { msg ->
            toolbar.title = msg
        })

        btnNext.setOnClickListener {
            forgotPasswordViewModel.authenticate("9988776655")

            newIntent(context,VerificationActivity::class.java,"")

        }


    }


}
