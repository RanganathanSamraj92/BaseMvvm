package development.app.checking.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.BuildConfig
import development.app.checking.data.repository.VersionsRepository
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AppVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    private val repository: VersionsRepository = VersionsRepository(RetrofitFactory.makeRetrofitService())

    private val appVersion: MutableLiveData<AppVersion> = MutableLiveData()

    val version = MutableLiveData<String>()
    init {
        loadAppVersion()

        version.value = "version : ${BuildConfig.VERSION_NAME}"

    }


    fun getAppVersion(): LiveData<AppVersion> {
        return appVersion
    }

    private fun loadAppVersion() {

        scope.launch {
            val apiResponse = repository.getAppVersion()
            val res= handleResponses(apiResponse!!)
            try {
                appVersion.postValue(res.data.appVersion)
            } catch (e: Exception) {
                /*ignore the exception*/
            }
        }

    }

    fun animateUI(view: View) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f

        )
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = 1

        //Either way you can add Listener like this
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.animate().alpha(0.0f).duration = 500


            }
        })
        view.visibility = View.VISIBLE
        view.animate()
            .alpha(1.0f).setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.startAnimation(rotateAnimation)
                }
            })
    }
}
