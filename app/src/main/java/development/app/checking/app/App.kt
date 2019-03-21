package development.app.checking.app

import android.app.Application
import development.app.checking.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Hind-Regular.ttf")
            .setFontAttrId(R.attr.fontPath).build())

    }
}