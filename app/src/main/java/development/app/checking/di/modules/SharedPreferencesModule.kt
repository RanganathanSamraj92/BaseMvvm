package development.app.checking.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
open class SharedPreferencesModule(val context: Context){

    @Provides
    fun provideSharedPref():SharedPreferences{
        return context.getSharedPreferences("appName",Context.MODE_PRIVATE)
    }

}