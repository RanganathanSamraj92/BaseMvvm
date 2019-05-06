package development.app.checking.model

import java.io.Serializable

class AndroidVersion : Serializable {
    val name: String = ""
    val version_code: String = ""
    val api_level: String = ""
    val release_date: String = ""
    lateinit var images: List<Image>

}

