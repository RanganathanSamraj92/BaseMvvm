package development.app.checking.data.repository

import development.app.checking.data.repository.remote.ApiCallInterface
import development.app.checking.model.AndroidVersion

open class MovieRepository(private val api : ApiCallInterface) : BaseRepository() {

     suspend fun getPopularMovies() : MutableList<AndroidVersion>?{

        val movieResponse = safeApiCall( call = {api.getVersions().await()})

        return movieResponse!!.data.results.toMutableList()

    }

}