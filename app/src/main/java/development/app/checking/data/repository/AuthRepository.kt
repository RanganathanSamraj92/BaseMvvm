package development.app.checking.data.repository

import development.app.checking.data.request.LoginRequest
import development.app.checking.data.request.RegisterRequest
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.UpdateFCMModel
import development.app.checking.model.VerifyTokenModel
import development.app.checking.viewmodel.LoginViewModel

class AuthRepository(private val apiService: AuthApiCallInterface) : BaseRepository() {


    suspend fun register(registerRequest: RegisterRequest): APIResponse? {
        return safeApiCall(call = { apiService.registerAsync(registerRequest).await() })
    }


    suspend fun me(token: String): APIResponse? {
        return safeApiCall(call = { apiService.profileAsync(token).await() })
    }

    suspend fun authenticate(emailOrMobile: String): APIResponse? {
        return safeApiCall(call = { apiService.authenticateAsync(emailOrMobile).await() })
    }

    suspend fun sendOTP(emailOrMobile: String): APIResponse? {
        return safeApiCall(call = { apiService.sendOTPAsync(emailOrMobile).await() })
    }

    suspend fun resetPassword(password: String): APIResponse? {
        return safeApiCall(call = { apiService.resetPasswordAsync(password).await() })
    }

    suspend fun login(verifyTokenModel: VerifyTokenModel): APIResponse? {
        return safeApiCall(call = { apiService.loginAsync(verifyTokenModel).await() })
    }

    suspend fun updateFCMToken(updateFCMModel: UpdateFCMModel): APIResponse? {
        return safeApiCall(call = { apiService.updateFCMTokenAsync(updateFCMModel).await() })
    }

    suspend fun updateIdToken(verifyTokenModel: VerifyTokenModel): APIResponse? {
        return safeApiCall(call = { apiService.updateIdTokenAsync(verifyTokenModel).await() })
    }


}