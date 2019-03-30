package development.app.checking.data.repository

import development.app.checking.data.request.ImageUploadRequest
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.ImageUploadAPICallInterface

class ImageUploadRepository(private val apiService: ImageUploadAPICallInterface) : BaseRepository() {


    suspend fun uploadImage(imageUploadRequest: ImageUploadRequest): APIResponse? {
        return safeApiCall(call = { apiService.uploadImageAsync(imageUploadRequest.token,imageUploadRequest.file,imageUploadRequest.name).await() })
    }




}