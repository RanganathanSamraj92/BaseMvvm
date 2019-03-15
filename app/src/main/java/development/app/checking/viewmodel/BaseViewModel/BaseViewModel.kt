package development.app.checking.viewmodel.BaseViewModel

import androidx.lifecycle.ViewModel
import android.util.Log

open class BaseViewModel : ViewModel(){

    override fun onCleared() {
        super.onCleared()
        Log.w("TAG","OnCleared")
    }
}