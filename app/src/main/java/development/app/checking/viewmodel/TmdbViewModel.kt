package development.app.checking.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import development.app.checking.data.repository.MovieRepository
import development.app.checking.data.repository.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TmdbViewModel : BaseViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : MovieRepository = MovieRepository(RetrofitFactory.makeRetrofitService())


    val popularMoviesLiveData = MutableLiveData<MutableList<AndroidVersion>>()

    fun fetchMovies(){
        scope.launch {
            val popularMovies = repository.getPopularMovies()
            popularMoviesLiveData.postValue(popularMovies)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}