package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.ImageUploadRepository
import development.app.checking.data.request.ImageUploadRequest
import development.app.checking.data.source.remote.ImageUploadAPICallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.UploadResModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ImageUploadViewModel : BaseViewModel() {


    @Inject
    lateinit var imageUploadAPICallInterface: ImageUploadAPICallInterface


    private val repository = ImageUploadRepository(imageUploadAPICallInterface)

    val uploadResModel = MutableLiveData<UploadResModel>()


    init {

    }

    fun uploadImage(token:String,file: MultipartBody.Part, name: RequestBody) {
        loadingStatus.value = true
        scope.launch {
            var imageUploadRequest = ImageUploadRequest()
            imageUploadRequest.file = file
            imageUploadRequest.name = name
            imageUploadRequest.token = token
            val apiResponse = repository.uploadImage(imageUploadRequest)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    uploadResModel.postValue(res.data.imageResModel)

                }
            } catch (e: Throwable) {

            }

        }
    }
}
