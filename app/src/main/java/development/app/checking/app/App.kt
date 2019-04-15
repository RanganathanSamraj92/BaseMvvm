package development.app.checking.app

import android.app.Application
import com.google.firebase.FirebaseApp
import development.app.checking.di.injectors.DaggerMyComponent
import development.app.checking.di.injectors.MyComponent
import development.app.checking.di.modules.SharedPreferencesModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class App : Application() {

    lateinit var myComponent: MyComponent


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

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