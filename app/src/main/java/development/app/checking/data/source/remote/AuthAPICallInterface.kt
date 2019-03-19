package development.app.checking.data.source.remote

import development.app.checking.model.LoginModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiCallInterface {

    @POST(NetworkUtils.login)
    fun loginAsync():  Deferred<Response<LoginModel>>



}