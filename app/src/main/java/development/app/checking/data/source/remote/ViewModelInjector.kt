package development.app.checking.data.source.remote

import dagger.Component
import dagger.Module
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import development.app.checking.viewmodel.DetailViewModel
import development.app.checking.viewmodel.LoginViewModel
import development.app.checking.viewmodel.SplashViewModel
import development.app.checking.viewmodel.VersionViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for ViewModels.
 */
@Singleton
@Component(modules = [NetworkModule::class,LocalNetworkModule::class])
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified BaseViewModel.
     * @param baseViewModel BaseViewModel in which to inject the dependencies
     */
    fun inject(splashViewModel: SplashViewModel)
    fun inject(versionViewModel: VersionViewModel)
    fun inject(detailViewModel: DetailViewModel)
    fun inject(loginViewModel: LoginViewModel)

    @Component.Builder
    interface Builder {
        fun build() : ViewModelInjector

        fun networkModule(networkModule: NetworkModule):Builder

        fun localNetworkModule(localNetworkModule: LocalNetworkModule):Builder
    }
}