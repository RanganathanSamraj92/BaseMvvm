package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import development.app.checking.model.UserModel

class MyViewModel : ViewModel() {

    private val users: MutableLiveData<List<UserModel>> by lazy {
        MutableLiveData<List<UserModel>>()
    }

    fun getUsers(): LiveData<List<UserModel>> {
        return users
    }





    fun loadUsers() {
        val user = UserModel()
        user.id = "abcd@gmail.com"
        user.name = "abcd@gmail.com"
        user.age = "abcd@gmail.com"
        user.email = "abcd@gmail.com"
        var lat = listOf(user)
        users.postValue(lat)
        // Do an asynchronous operation to fetch users.
    }
}