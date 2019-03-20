package development.app.checking.data.response

import com.google.gson.annotations.SerializedName
import development.app.checking.model.AndroidVersion
import development.app.checking.model.AppVersion
import development.app.checking.model.LoginResult
import development.app.checking.model.VersionDetail

open class ResponseData{

    @SerializedName("versions")
    open var  versions: List<AndroidVersion> = listOf()

    @SerializedName("app_version")
    open lateinit var  appVersion: AppVersion

    @SerializedName("version_details")
    open lateinit var  versionDetails: VersionDetail


    @SerializedName("result")
    open lateinit var  result: LoginResult
}