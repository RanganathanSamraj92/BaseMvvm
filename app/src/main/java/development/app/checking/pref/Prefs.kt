package development.app.checking.pref

import android.content.SharedPreferences

/*simple class for SharePreferences
* base Prefs's functions
*/
class Prefs(private val sharedPreferences: SharedPreferences) {


    /*insert*/
    fun putData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /*retrieve*/
    fun getData(key: String) : String{
        return sharedPreferences.getString(key,"")
    }
}