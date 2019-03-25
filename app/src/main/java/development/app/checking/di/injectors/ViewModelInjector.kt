package development.app.checking.di.injectors

import dagger.Component
import development.app.checking.di.modules.LocalNetworkModule
import development.app.checking.di.modules.NetworkModule
import development.app.checking.di.modules.SharedPreferencesModule
import development.app.checking.viewmodel.*
import javax.inject.Singleton

/**
 * Component providing inject() methods for ViewModels.
 */
@Singleton
@Component(
    modules = [NetworkModule::class,
        LocalNetworkModule::class,
        SharedPreferencesModule::class]
)
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified BaseViewModel.
     * @param baseViewModel BaseViewModel in which to inject the dependencies
     */
    fun inject(splashViewModel: SplashViewModel)

    fun inject(versionViewModel: VersionViewModel)
    fun inject(detailViewModel: DetailViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(registerViewModel: RegisterViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun localNetworkModule(localNetworkModule: LocalNetworkModule): Builder

        fun prefsModule(sharedPreferencesModule: SharedPreferencesModule): Builder
    }
}