package development.app.checking.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import development.app.checking.model.AppVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Job

class HomeViewModel : BaseViewModel() {


    private val appVersion: MutableLiveData<AppVersion> = MutableLiveData()

    init {

    }


    private lateinit var request: Job

    internal fun saveUser(user:HashMap<String, Any>) {
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

    internal fun getUsers(key:String,value:String) {
        firestoreDB.collection("users")
            .whereEqualTo(key,value)
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

}
