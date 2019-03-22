package development.app.checking.app

import android.app.Application
import development.app.checking.R
import development.app.checking.data.source.remote.DaggerViewModelInjector
import development.app.checking.di.modules.LocalNetworkModule
import development.app.checking.di.modules.NetworkModule
import development.app.checking.di.injectors.ViewModelInjector
import development.app.checking.di.modules.SharedPreferencesModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App: Application() {

     val injector: ViewModelInjector = DaggerViewModelInjector.builder()
        .networkModule(networkModule = NetworkModule)
        .localNetworkModule(localNetworkModule = LocalNetworkModule)
        .prefsModule(sharedPreferencesModule = SharedPreferencesModule(this))
         .build()

    var myObj:String = ""
    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Hind-Regular.ttf")
            .setFontAttrId(R.attr.fontPath).build())

    }

}