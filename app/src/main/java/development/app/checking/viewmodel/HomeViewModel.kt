package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import development.app.checking.model.AppVersion
import development.app.checking.model.ProfileModel
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Job

class HomeViewModel : BaseViewModel() {


    val userInfo = MutableLiveData<ProfileModel>()

    init {

    }


    private lateinit var request: Job

    internal fun saveUser(user: HashMap<String, Any>) {
        // Add a new document with a generated ID
        firestoreDB.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.w(TAG_FIRESTORE, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG_FIRESTORE, "Error adding document", e)
            }
    }

    internal fun getUsers(key: String, value: String) {
        firestoreDB.collection("users")
            .whereEqualTo(key, value)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.w(TAG_FIRESTORE, " id : ${document.id} data => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG_FIRESTORE, "Error getting documents.", exception)
            }
    }


    override fun onCleared() {

        request.cancel()
        super.onCleared()
    }

    fun getProfile(token: String) {
        databaseRef = database.getReference("users/${auth.currentUser!!.uid}")

        loadingStatus.value = true
        databaseRef.addValueEventListener(object : ValueEventListener {
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
