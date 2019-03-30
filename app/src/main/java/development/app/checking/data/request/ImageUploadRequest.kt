package development.app.checking.data.request

import okhttp3.MultipartBody

class ImageUploadRequest{
    lateinit var imagePart:MultipartBody.Part
    lateinit var name:MultipartBody
}