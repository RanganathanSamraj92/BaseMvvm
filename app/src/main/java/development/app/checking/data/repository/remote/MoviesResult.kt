package development.app.checking.data.repository.remote

sealed class MoviesResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : MoviesResult<T>()
    data class Error(val exception: Exception) : MoviesResult<Nothing>()
}