package development.app.checking.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideVersionsApi(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create(ApiCallInterface::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitFactory.LOCAL_BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            /*.addConverterFactory(MoshiConverterFactory.create())*/
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}