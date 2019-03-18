package development.app.checking.data.repository

import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.ApiCallInterface

class VersionsRepository(private val apiService: ApiCallInterface): BaseRepository() {

    suspend fun getOSVersions(): APIResponse? {
        return safeApiCall(call = { apiService.getVersionsAsync().await() })
    }

    suspend fun getAppVersion(): APIResponse? {
        return safeApiCall(call = { apiService.getAppVersionAsync().await() })
    }

    suspend fun getAppVersionDetail(): APIResponse? {
        return safeApiCall(call = { apiService.getAppVersionDetailAsync().await() })
    }
}