package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mukesh.OtpView
import development.app.checking.R
import development.app.checking.data.source.remote.NetworkUtils.Companion.login
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.LoginViewModel
import development.app.checking.viewmodel.VerificationViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_verification.*


class VerificationActivity : BaseActivity() {

    private lateinit var verificationViewModel: VerificationViewModel

    private var code:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.content_verification)
        setAppBar(resources.getString(R.string.title_activity_verification))
        verificationViewModel = ViewModelProviders.of(this).get(VerificationViewModel::class.java)
        viewModelSetup(this, verificationViewModel)

        verificationViewModel.sendOTPResult.observe(this, Observer { otpModel ->
            toolbar_layout.title = otpModel.code
            code = otpModel.code

        })

        fab.setOnClickListener {
           validate(txtOTP.text.toString())
        }


        txtOTP.setOtpCompletionListener {
            otp ->  validate(otp)
        }

        txtResendOTP.setOnClickListener {
            resendOTP()
        }




    }

    private fun validate(otp:String) {
        if (otp.isEmpty()){
            showMsg(txtOTP,"Verification code cannot be empty!")
            return
        }

        if (code==otp){
            showMsg(txtOTP,"Verified successfully....")
        }else{
            showMsg(txtOTP,"Verification failed \nincorrect code....")

            newIntent(context,ResetPasswordActivity::class.java,"")
        }
    }

    private fun resendOTP() {
        showMsg(txtOTP,"Resending....")
    }


}
