package development.app.checking.data.response.base

import com.google.gson.annotations.SerializedName
import development.app.checking.model.*

open class ResponseData {

    @SerializedName("versions")
    open var versions: List<AndroidVersion> = listOf()

    @SerializedName("app_version")
    open lateinit var appVersion: AppVersion

    @SerializedName("version_details")
    open lateinit var versionDetails: VersionDetail


    @SerializedName("result")
    open lateinit var loginModel: LoginModel

    @SerializedName("user_info")
    open lateinit var userInfo: ProfileModel

    @SerializedName("imageUploadResult")
    open lateinit var imageResModel: UploadResModel

    @SerializedName("otp_result")
    open lateinit var sendOTPModel: SendOTPModel
}