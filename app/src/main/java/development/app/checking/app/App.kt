package development.app.checking.app

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import development.app.checking.di.injectors.DaggerMyComponent
import development.app.checking.di.injectors.MyComponent
import development.app.checking.di.modules.SharedPreferencesModule
import development.app.checking.utils.ForceUpdateChecker
import io.fabric.sdk.android.Fabric
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.*


class App : Application() {

    lateinit var myComponent: MyComponent


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        // set in-app defaults
        val remoteConfigDefaults = HashMap<String, Any>()
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false)
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.1")
        remoteConfigDefaults.put(
            ForceUpdateChecker.KEY_UPDATE_URL,
            "https://play.google.com/store/apps/details?id=com.google.android.apps.translate"
        )

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults)
        firebaseRemoteConfig
            .fetch(60) // fetch every minutes
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.w("remote", "remote config is fetched.")
                    firebaseRemoteConfig.fetchAndActivate()
                }
            }

        /*Dagger component initialization
        * here is initialization for MyComponent
        * prefModule() is a method of MyComponent interface*/
        myComponent = DaggerMyComponent.builder()
            .prefModule(sharedPreferencesModule = SharedPreferencesModule(this))
            .build()

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Hind-Regular.ttf")
                .setFontAttrId(development.app.checking.R.attr.fontPath).build()
        )
    }


}