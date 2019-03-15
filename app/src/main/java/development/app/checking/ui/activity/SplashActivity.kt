package development.app.checking.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.WindowManager
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        splashViewModel.getAppVersion().observe(this@SplashActivity, Observer {
            txtAppVersion.text = it!!.version
            showMsg(txtAppVersion,it!!.version)
            GlobalScope.launch {
                newIntent(this@SplashActivity,AndroidVersionActivity::class.java)
            }
        })

    }
}
