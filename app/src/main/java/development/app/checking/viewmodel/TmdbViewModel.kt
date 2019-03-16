package development.app.checking.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import development.app.checking.data.repository.MovieRepository
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.data.source.remote.RetrofitFactory
import development.app.checking.model.AndroidVersion
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TmdbViewModel : BaseViewModel(){


    private val repository : MovieRepository = MovieRepository(RetrofitFactory.makeRetrofitService())


    val popularMoviesLiveData = MutableLiveData<MutableList<AndroidVersion>>()

    fun fetchMovies(it: View){
        scope.launch {
            val popularMovies = repository.getPopularMovies(it)
            popularMoviesLiveData.postValue(popularMovies)
            when (popularMovies) {
                is APIResponse.Success -> popularMoviesLiveData.postValue(popularMovies.data.versions.toMutableList())
                //is APIResponse.Error -> _error.postValue(value.exception.message)
            }
        }
    }



}