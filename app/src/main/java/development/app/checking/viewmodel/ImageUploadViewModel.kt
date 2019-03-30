package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.ImageUploadRepository
import development.app.checking.data.request.ImageUploadRequest
import development.app.checking.data.source.remote.ImageUploadAPICallInterface
import development.app.checking.model.LoginModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUploadViewModel : BaseViewModel() {


    @Inject
    lateinit var imageUploadAPICallInterface: ImageUploadAPICallInterface


    private val repository = ImageUploadRepository(imageUploadAPICallInterface)

    val loginResult = MutableLiveData<LoginModel>()


    init {

    }

    fun uploadImage(email: MultipartBody.Part, name: MultipartBody) {
        loadingStatus.value = true
        scope.launch {
            var imageUploadRequest = ImageUploadRequest()
            imageUploadRequest.imagePart = email
            imageUploadRequest.name = name
            val apiResponse = repository.uploadImage(imageUploadRequest)
            val res = handleResponses(apiResponse!!)
            try {
                if (res.meta.status) {
                    loginResult.postValue(res.data.loginModel)

                }
            } catch (e: Throwable) {

            }

        }
    }
}
