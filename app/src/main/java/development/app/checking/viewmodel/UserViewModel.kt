package development.app.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : BaseViewModel() {

    var firstName: MutableLiveData<String> = MutableLiveData()
    var status: MutableLiveData<String> = MutableLiveData()

    fun getName(): LiveData<String> {
        return firstName
    }

    fun getStatus(): LiveData<String> {
        return status
    }

    private val items = MutableLiveData<List<String>>()

    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    init {
        loading.postValue(false)
        if (firstName.value == null) {
            loadBooks()
        } else {
            status.postValue("already loaded")
        }
    }

    fun onLikeClick() {
        loadBooks()

    }

    private fun loadBooks() {
        loading.value = true
        GlobalScope.launch {
            delay(3000)
            launch(Dispatchers.Main) {
                firstName.postValue("I have changed after 10 seconds")
                // This method will be executed once the timer is over
                //firstName.value = "Ranganathan"
                items.value = listOf("")
                loading.value = false
            }

        }


    }


}