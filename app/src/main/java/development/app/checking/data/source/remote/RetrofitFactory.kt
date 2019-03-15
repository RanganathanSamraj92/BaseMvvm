package development.app.checking.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitFactory {


    val BASE_URL = "https://jsonplaceholder.typicode.com"

    companion object {
        val BASE_URL1 = "http://shamlatech.net/android_portal/kotlinApps/api/v1/"
        fun makeRetrofitService(): ApiCallInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                /*.addConverterFactory(MoshiConverterFactory.create())*/
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(ApiCallInterface::class.java)
        }

    }


}