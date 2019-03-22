package development.app.checking.di.modules

import dagger.Module
import dagger.Provides
import dagger.Reusable
import development.app.checking.data.source.remote.AuthApiCallInterface
import retrofit2.Retrofit

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object LocalNetworkModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAuthApi(retrofit: Retrofit): AuthApiCallInterface {
        return retrofit.create(AuthApiCallInterface::class.java)
    }


}