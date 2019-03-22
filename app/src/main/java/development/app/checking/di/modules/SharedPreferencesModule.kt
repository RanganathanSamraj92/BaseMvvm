package development.app.checking.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import development.app.checking.app.App

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object SharedPreferencesModule{

    @Provides
    fun provideSharedPref():SharedPreferences{
        return App().baseContext.getSharedPreferences("appName",Context.MODE_PRIVATE)
    }

}