package development.app.checking.data.source.remote

import development.app.checking.data.request.LoginRequest
import development.app.checking.data.request.RegisterRequest
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ImageUploadAPICallInterface {

    @Multipart
    @POST(NetworkUtils.uploadPhoto)
    fun uploadImageAsync(@Part imagePartBody : MultipartBody.Part, @Part("filename") other:RequestBody )
            : Deferred<Response<APIResponse>>





}