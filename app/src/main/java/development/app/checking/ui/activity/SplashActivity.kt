package development.app.checking.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.BuildConfig
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.widget.Toast
import android.view.View


class SplashActivity : BaseActivity() {

    lateinit var mDelayHandler: Handler
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.activity_splash)
        app_bar.visibility = GONE
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        txtAppVersion.text = BuildConfig.VERSION_NAME

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, 3500)


    }

    val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            viewModelSetup(this, splashViewModel)
            splashViewModel.getAppVersion().observe(this@SplashActivity, Observer {
                if (BuildConfig.VERSION_NAME.equals(it!!.appVersion.version)){
                    GlobalScope.launch {
                        newIntent(this@SplashActivity, AndroidVersionActivity::class.java)
                    }
                }else{
                    if (it!!.appVersion.mandatory == "1") {
                        showAlert("Update!","New Version Available v:${it!!.appVersion.version} \ncurrent v:${BuildConfig.VERSION_NAME}",
                            object : Utils.OnClickListener {
                                override fun onClick(v: View) {
                                    Toast.makeText(v.getContext(), "Click", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }


            })
        }
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
