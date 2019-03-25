package development.app.checking.di.injectors

import dagger.Component
import development.app.checking.di.modules.SharedPreferencesModule
import development.app.checking.ui.activity.auth.LoginActivity
import android.content.SharedPreferences
import android.app.Application
import dagger.BindsInstance
import development.app.checking.app.App
import development.app.checking.pref.Prefs
import development.app.checking.ui.activity.SplashActivity
import development.app.checking.ui.activity.profile.ProfileActivity
import development.app.checking.ui.base.BaseActivity
import javax.inject.Singleton


@Singleton
@Component(
    modules = [SharedPreferencesModule::class]
)
interface MyComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(splashActivity: SplashActivity)

    /*injects  Prefs instance in Base*/
    fun inject(baseActivity: BaseActivity)



    @Component.Builder
    interface builder{
        fun build():MyComponent

        /*Prefs'module declarations*/
        fun prefModule(sharedPreferencesModule: SharedPreferencesModule): builder
    }



}