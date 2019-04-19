package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import development.app.checking.data.repository.AuthRepository
import development.app.checking.data.source.remote.AuthApiCallInterface
import development.app.checking.model.ProfileModel
import development.app.checking.model.UploadResModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import javax.inject.Inject


class ProfileViewModel : ImageUploadViewModel() {



    @Inject
    lateinit var authApiCall: AuthApiCallInterface


    private val repository = AuthRepository(authApiCall)

    val userInfo = MutableLiveData<ProfileModel>()




    init {


    }

    fun getProfile(token: String) {

        loadingStatus.value = true
        msgRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val profile = dataSnapshot.getValue(ProfileModel::class.java)
                Log.w("profile", "Value is: ${profile!!.name}  ${profile.email} ")
                userInfo.postValue(profile)
                loadingStatus.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                loadingStatus.value = false
                // Failed to read value
                Log.w("profile", "Failed to read value.", error.toException())
            }
        })
    }


}
