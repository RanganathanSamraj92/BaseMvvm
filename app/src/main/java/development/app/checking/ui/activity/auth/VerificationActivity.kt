package development.app.checking.ui.activity.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.data.request.RegisterRequest
import development.app.checking.ui.activity.HomeActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.VerificationViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_verification.*


class VerificationActivity : BaseActivity() {

    private lateinit var verificationViewModel: VerificationViewModel

    private var code: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.content_verification)
        setAppBar(resources.getString(R.string.title_activity_verification))
        verificationViewModel = ViewModelProviders.of(this).get(VerificationViewModel::class.java)
        viewModelSetup(this, verificationViewModel)

        verificationViewModel.storedVerificationId.observe(this, Observer { otpId ->
            showMsg(txtResendOTP, "OTP sent!")

        })
        verificationViewModel.onVerify.observe(this, Observer { credential ->
            showMsg(txtResendOTP, "verifying..")
            verificationViewModel.signInWithPhoneAuthCredential(this@VerificationActivity, credential)

        })
        verificationViewModel.userAvailability.observe(this, Observer { otpResult ->
            if (otpResult.availableStatus) {
                verificationViewModel.updateIdToken(otpResult.registerRequest.idToken, otpResult.registerRequest.uid)
            } else {
                newIntent(this@VerificationActivity, OTPRegisterActivity::class.java, otpResult.registerRequest)
                finish()
            }

        })

        verificationViewModel.updateIdTokenResult.observe(this, Observer { isUpdated ->
            if (isUpdated.status) {
                newIntent(this@VerificationActivity, HomeActivity::class.java, "")
                finish()
            } else {
                showMsg(txtResendOTP, "Token not updated try again!..")
            }

        })


        verificationViewModel.phoneSignInResult.observe(this, Observer { signInResult ->
            if (signInResult.status) {
                verificationViewModel.saveToken(signInResult.token, signInResult.uid)
            } else {
                showMsg(txtResendOTP, signInResult.msg)
            }

        })

        fab.setOnClickListener {
            validate(txtOTP.text.toString())
        }


        txtOTP.setOtpCompletionListener { otp ->
            validate(otp)
        }

        txtResendOTP.setOnClickListener {
            verificationViewModel.sendCode(this@VerificationActivity, "9080515605")
        }


    }

    private fun validate(otp: String) {
        if (otp.isEmpty()) {
            showMsg(txtOTP, "Verification code cannot be empty!")
            return
        }
        verificationViewModel.verifyVerificationCode(this@VerificationActivity, otp)
    }

    private fun resendOTP() {
        showMsg(txtOTP, "Resending....")
    }


}
