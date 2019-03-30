package development.app.checking.data.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImageUploadRequest{
    lateinit var file:MultipartBody.Part
    lateinit var name: RequestBody
    lateinit var token: String
}