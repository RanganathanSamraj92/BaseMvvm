package development.app.checking.data.repository

sealed class OTPResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : OTPResult<T>()
    data class Error(val code: Any,val message: Any) : OTPResult<Nothing>()
    data class Faliure(val exception: Exception) : OTPResult<Nothing>()
}