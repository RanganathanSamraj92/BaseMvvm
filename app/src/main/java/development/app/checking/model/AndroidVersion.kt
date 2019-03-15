package development.app.checking.model

class AndroidVersion{
    val name: String = ""
    val version_code: String = ""
    val api_level: String = ""
    val release_date: String = ""
    lateinit var  images: List<Image>

}

data class TmdbMovieResponse(
    val results: List<AndroidVersion>
)