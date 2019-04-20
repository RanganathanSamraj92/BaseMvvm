package development.app.checking.data.source.remote

import development.app.checking.data.request.LoginRequest
import development.app.checking.data.request.RegisterRequest
import development.app.checking.model.UpdateFCMModel
import development.app.checking.model.VerifyTokenModel
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

    @POST(NetworkUtils.sendOTP)
    fun sendOTPAsync(@Body emailOrMobile :String ): Deferred<Response<APIResponse>>

    @POST(NetworkUtils.resetPassword)
    fun resetPasswordAsync(@Body password :String ): Deferred<Response<APIResponse>>


    @POST(NetworkUtils.login)
    fun loginAsync(@Body verifyTokenModel: VerifyTokenModel): Deferred<Response<APIResponse>>

    @POST(NetworkUtils.updateFCMToken)
    fun updateFCMTokenAsync(@Body updateFCMModel: UpdateFCMModel): Deferred<Response<APIResponse>>

    @POST(NetworkUtils.updateIdToken)
    fun updateIdTokenAsync(@Body verifyTokenModel: VerifyTokenModel): Deferred<Response<APIResponse>>


}