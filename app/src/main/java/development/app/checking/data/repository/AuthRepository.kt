package development.app.checking.data.repository

import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.viewmodel.LoginViewModel

class AuthRepository(private val apiService: AuthApiCallInterface): BaseRepository() {


    suspend fun login(): LoginViewModel? {
        return null//safeApiCall(call = { apiService.loginAsync().await() })
    }


}