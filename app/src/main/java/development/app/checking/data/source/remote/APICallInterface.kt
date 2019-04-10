package development.app.checking.data.source.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiCallInterface {

    @GET(NetworkUtils.getAndroidVersions)
    fun getVersionsAsync():  Deferred<Response<APIResponse>>

    @GET(NetworkUtils.checkVersion)
    fun getAppVersionAsync():  Deferred<Response<APIResponse>>

    @GET(NetworkUtils.versionDetails)
    fun getAppVersionDetailAsync():  Deferred<Response<APIResponse>>

    @GET(NetworkUtils.demoformat)
    fun getAppAsync():  Deferred<Response<APIResponse>>

}