package development.app.checking.model

import com.google.gson.annotations.SerializedName

class AppVersion{
    @SerializedName("android")
    lateinit var appVersion:AndroidAppVersion
}