package development.app.checking.data.source.remote

open class NetworkUtils {
    companion object {
        const val checkVersion: String = "versions.json"
        const val versionDetails: String = "version_details.json"
        const val getAndroidVersions: String = "android_versions.json"
        const val login: String = "auth/login"
        const val register: String = "auth/register"
        const val me: String = "auth/me"
        const val uploadPhoto: String = "api/upload/photo"
        const val authenticate: String = "api/authenticate"
    }
}