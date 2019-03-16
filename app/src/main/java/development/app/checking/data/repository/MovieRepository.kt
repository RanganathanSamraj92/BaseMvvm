package development.app.checking.data.repository

import android.view.View
import development.app.checking.data.source.remote.ApiCallInterface
import development.app.checking.model.AndroidVersion

open class MovieRepository(private val api : ApiCallInterface) : BaseRepository() {

     suspend fun getPopularMovies(view :View) : MutableList<AndroidVersion>?{

        val movieResponse = safeApiCall( call = {api.getVersions().await()})

        return movieResponse!!.data.versions.toMutableList()

    }

}