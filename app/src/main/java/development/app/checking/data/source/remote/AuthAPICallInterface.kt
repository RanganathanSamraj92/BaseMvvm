package development.app.checking.data.source.remote

import development.app.checking.data.request.LoginRequest
import development.app.checking.data.request.RegisterRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface AuthApiCallInterface {

    @POST(NetworkUtils.login)
    fun loginAsync(@Body newPart : LoginRequest): Deferred<Response<APIResponse>>


    @POST(NetworkUtils.register)
    fun registerAsync(@Body register : RegisterRequest): Deferred<Response<APIResponse>>


    @GET(NetworkUtils.me)
    fun profileAsync(@Header("x-access-token") token :String ): Deferred<Response<APIResponse>>

    @POST(NetworkUtils.authenticate)
    fun authenticateAsync(@Body emailOrMobile :String ): Deferred<Response<APIResponse>>


}