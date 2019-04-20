package development.app.checking.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import development.app.checking.data.repository.ImageUploadRepository
import development.app.checking.data.request.ImageUploadRequest
import development.app.checking.data.source.remote.ImageUploadAPICallInterface
import development.app.checking.model.LoginModel
import development.app.checking.model.UploadResModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*
import javax.inject.Inject

open class ImageUploadViewModel : BaseViewModel() {


    var uploadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    internal lateinit var msgRef: DatabaseReference

    val storage = FirebaseStorage.getInstance()

    @Inject
    lateinit var imageUploadAPICallInterface: ImageUploadAPICallInterface


    private val repository = ImageUploadRepository(imageUploadAPICallInterface)

    val uploadResModel = MutableLiveData<UploadResModel>()


    init {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        msgRef = database.getReference("users/${auth.currentUser!!.uid}")

        //msgRef.setValue("Hello, World!")
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

    internal fun upload(uri: Uri) {

        uploadingStatus.value = true

        val storageRef = storage.reference
        var mReference = storageRef.child("images/${Calendar.getInstance().timeInMillis.toString() }.jpg")
        try {
            mReference.putFile(uri).addOnSuccessListener {taskSnapshot: UploadTask.TaskSnapshot? ->
                val url = taskSnapshot!!.storage.downloadUrl
                url.addOnSuccessListener {
                    uri -> Log.w("uri : ",uri.toString())
                    msgRef.child("photoUrl").setValue(uri.toString())
                    uploadingStatus.value = false
                }
            }
        }catch (e: Exception) {
            uploadingStatus.value = false
            Log.w("upload Exception : ",e.localizedMessage)
        }

    }
}
