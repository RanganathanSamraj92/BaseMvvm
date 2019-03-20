package development.app.checking.data.source.remote

import development.app.checking.data.request.LoginRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiCallInterface {

    @POST(NetworkUtils.login)
    fun loginAsync(@Body newPart : LoginRequest): Deferred<Response<APIResponse>>


}