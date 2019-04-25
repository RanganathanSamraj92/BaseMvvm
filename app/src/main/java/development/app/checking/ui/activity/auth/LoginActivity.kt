package development.app.checking.ui.activity.auth

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.simplepass.loadingbutton.animatedDrawables.ProgressType
import br.com.simplepass.loadingbutton.customViews.ProgressButton
import development.app.checking.R
import development.app.checking.pref.PrefMgr
import development.app.checking.pref.Prefs
import development.app.checking.ui.activity.HomeActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel


    @Inject
    lateinit var myPref: Prefs

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_login)
        setAppBarCollapse(resources.getString(R.string.app_name))
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModelSetup(this, loginViewModel)

        inject()

        showProgress = false
        loginViewModel.errorStatus.observe(this, Observer { error ->

            btnLogin.revertAnimation()

            // btnLogin .doneLoadingAnimation(defaultColor(this@LoginActivity), defaultDoneImage(this@LoginActivity.resources))
        })


        loginViewModel.loginResult.observe(this, Observer { login ->
            btnLogin.revertAnimation()
            toolbar_layout.title = login.message
            makeLog(login.message)
            token = login.token;
            loginViewModel.updateLoginIdToken( login.token,login.uid)


        })
        loginViewModel.updateFCMResult.observe(this, Observer { isUpdated ->
           if (isUpdated){
               myPref.putData(PrefMgr.KEY_ACCESS_TOKEN, token)
               newIntent(this@LoginActivity, HomeActivity::class.java, "")
               finish()
           }else{
               loginViewModel.signOut()
               makeLog("Retry!")
           }
        })

        btnLogin.setOnClickListener {
            loginViewModel.signIn(txtEmail.text.toString(), txtPassword.text.toString())
            btnLogin.progressType = ProgressType.INDETERMINATE
            btnLogin.startAnimation()
            // progressAnimator(buttonTest6).start()

        }

        txtSignUp.setOnClickListener {
            newIntent(this@LoginActivity, RegisterActivity::class.java, "")
        }

        txtForgotPassword.setOnClickListener {
            newIntent(this@LoginActivity, ForgotPasswordActivity::class.java, "")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        btnLogin.dispose()
    }

    private fun ProgressButton.morphDoneAndRevert(
        context: Context,
        fillColor: Int = defaultColor(context),
        bitmap: Bitmap = defaultDoneImage(context.resources),
        doneTime: Long = 3000,
        revertTime: Long = 4000
    ) {
        progressType = ProgressType.INDETERMINATE
        startAnimation()
        Handler().run {
            postDelayed({ doneLoadingAnimation(fillColor, bitmap) }, doneTime)
            postDelayed({ revertAnimation() }, revertTime)
        }
    }

    private fun progressAnimator(progressButton: ProgressButton) = ValueAnimator.ofFloat(0F, 100F).apply {
        /*addUpdateListener { animation ->
            progressButton.setProgress(animation.animatedValue as Float)
        }*/
    }

    private fun defaultColor(context: Context) = ContextCompat.getColor(context, R.color.colorAccent)

    private fun defaultDoneImage(resources: Resources) =
        BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp)

    private fun ProgressButton.morphStopRevert(stopTime: Long = 1000, revertTime: Long = 2000) {
        startAnimation()
        Handler().postDelayed({ stopAnimation() }, stopTime)
        Handler().postDelayed({ revertAnimation() }, revertTime)
    }

}
