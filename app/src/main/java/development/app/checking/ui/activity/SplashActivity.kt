package development.app.checking.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.BR
import development.app.checking.BuildConfig
import development.app.checking.R
import development.app.checking.databinding.ActivitySplashBinding
import development.app.checking.pref.PrefMgr
import development.app.checking.pref.Prefs
import development.app.checking.ui.activity.android_verisons.AndroidVersionActivity
import development.app.checking.ui.activity.auth.LoginActivity
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : BaseActivity() {

    lateinit var mDelayHandler: Handler
    private lateinit var splashViewModel: SplashViewModel

  /*  @Inject
    lateinit var prefs: Prefs*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        inject()

        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        binding.setVariable(BR.splashViewModel, splashViewModel)


        splashViewModel.animateUI(imageSettings)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler.postDelayed(mRunnable, 3500)


    }


    val mRunnable: Runnable = Runnable {
        if (splashViewModel.auth.currentUser!=null){
            if (prefs.getData(PrefMgr.KEY_ACCESS_TOKEN).equals("")) {
                splashViewModel.auth.signOut()
                newIntent(this@SplashActivity, LoginActivity::class.java, "")
            }else{
                newIntent(this@SplashActivity, HomeActivity::class.java, "")
            }
        }else{
            newIntent(this@SplashActivity, LoginActivity::class.java, "")
        }


       /* if (!isFinishing) {
            //viewModelSetup(this, splashViewModel)
            splashViewModel.getAppVersion().observe(this@SplashActivity, Observer {
                if (BuildConfig.VERSION_NAME.equals(it!!.appVersion.version)) {
                    newIntent(this@SplashActivity, AndroidVersionActivity::class.java, "")

                } else {
                    if (it!!.appVersion.mandatory == "1") {
                        showAlert("Update!",
                            "New Version Available v:${it!!.appVersion.version} \ncurrent v:${BuildConfig.VERSION_NAME}",
                            object : Utils.OnClickListener {
                                override fun onClick(v: View) {
                                    // Toast.makeText(v.getContext(), "Click", Toast.LENGTH_SHORT).show()
                                    showMsg(txtAppVersion, "Click")
                                }
                            },
                            object : Utils.OnClickListener {
                                override fun onClick(v: View) {
                                    if (prefs.getData(KEY_TOKEN) == "") {
                                        newIntent(this@SplashActivity, LoginActivity::class.java, "")
                                    }else{
                                        newIntent(this@SplashActivity, HomeActivity::class.java, "")
                                    }
                                }
                            }
                        )
                    }
                }


            })
        }*/
    }

    override fun onBackPressed() {
        parentJob.cancel()
        super.onBackPressed()
    }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
