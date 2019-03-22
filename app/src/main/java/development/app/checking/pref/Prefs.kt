package development.app.checking.pref

import android.content.SharedPreferences

class Prefs(private val sharedPreferences: SharedPreferences) {


    fun putData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String,def:String) {
        sharedPreferences.getString(key,def)
    }
}