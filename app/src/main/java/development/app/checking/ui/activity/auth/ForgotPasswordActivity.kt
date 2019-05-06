package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren


class ForgotPasswordActivity : BaseActivity() {

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel


    private lateinit var k: Unit

    private lateinit var kl: Observer<String>
    private lateinit var errorObs: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.content_forgot_password)
        setAppBar(resources.getString(R.string.title_activity_forgot_password))
        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        viewModelSetup(this, forgotPasswordViewModel)



        kl = Observer { error ->
            newIntent(context, VerificationActivity::class.java, "")
        }

        errorObs = Observer { msg ->
            toolbar.title = msg
        }

        forgotPasswordViewModel.errorStatus.observe(this, kl)

        //forgotPasswordViewModel.metaStatus.observe(this, errorObs)

        btnNext.setOnClickListener {
            forgotPasswordViewModel.authenticate("9988776655")

        }


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onBackPressed() {
        super.onBackPressed()


    }


}
