package development.app.checking.data.source.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiCallInterface {

    /*@GET("api/someRandomPath")
    fun getOTPForNumber(@Query("mobile_number") mobileNumber: String): Deferred<Response>

    @GET("movie/popular")
    fun getPopularMovies() : Deferred<Response<MoviesResult.Success>>*/

    @GET("android_versions.json")
    fun getVersionsAsync(): Deferred<APIResponse>

    @GET("android_versions.json")
    fun getVersions():  Deferred<Response<APIResponse>>
}