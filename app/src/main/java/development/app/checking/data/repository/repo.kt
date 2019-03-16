package development.app.checking.data.repository

import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.ApiCallInterface

class VersionsRepository(private val apiService: ApiCallInterface): BaseRepository() {

    suspend fun getOSVersions(): APIResponse? {
        return safeApiCall(call = { apiService.getVersions().await() })
    }

    suspend fun getAppVersion(): APIResponse? {
        return safeApiCall(call = { apiService.getAppVersion().await() })
    }
}