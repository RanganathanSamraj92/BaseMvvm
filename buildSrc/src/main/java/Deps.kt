object Versions{
    val kotlin_version = "1.3.21"
    val gradle_version = "3.2.0"
    val android_plugin_version = "3.1.0"
    val support_lib = "27.1.0"
    val appcompat = "1.0.0-beta01"
    val retrofit = "2.4.0"
    val rxjava = "2.1.9"
    val constraint_layout = "1.1.3"
    val junit = "4.12"
    val junit_runner = "1.0.1"
    val espresso_core = "3.0.1"

    val appcompat_version = "1.0.0-beta01"
    val recyclerview_version = "1.0.0"
    val lifecycle_extensions_version = "2.0.0-beta01"
    val databinding_version = "3.2.0"
    val palette_version = "1.0.0"
    val dagger2_version = "2.16"
    val caligraphy_version = "2.3.0"
    val material_edittext_version = "2.1.4"
    val sdp_version = "1.0.6"
    val rtp_version = "1.1.0"

    val material_version = "1.0.0-beta01"
    val constraint_version = "1.1.3"

    val coroutines_version = "1.0.0"
    val picasso_version = "2.71828"
    val circle_imageview_version = "3.0.0"

    val retrofit_version = "2.5.0"
    val retrofit_gson_version = "2.3.0"
    val retrofit_adapter_version = "0.9.2"
    val moshi_version = "2.5.0"

    val retrofit_interceptor_version = "3.9.1"

    val test_junit_version = "4.12"
    val runner_version = "1.1.0-alpha4"
    val espresso_core_version = "3.1.0-alpha4"
}


object Libs {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    val design = "com.android.support:design:${Versions.support_lib}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview_version}"
    val cardview = "com.android.support:cardview-v7:${Versions.support_lib}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"





    val runner = "androidx.test:runner:${Versions.runner_version}"
    val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core_version}"


    //implementation 'androidx.legacy:legacy-support-v4:1.0.0-beta01'
    //implementation 'com.google.android.material:material:1.0.0-beta01'
    //implementation 'androidx.appcompat:appcompat:1.0.0-beta01'
    // implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

    val material = "com.google.android.material:material:${Versions.material_version}"
    /*databinding*/
    val databinding = "androidx.databinding:databinding-compiler:${Versions.lifecycle_extensions_version}"

    /*viewModel*/
    val viewModelExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extensions_version}"


    /*coroutines*/
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"

    val converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_version}"

    val retrofit2_kotlin_coroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_adapter_version}"

    val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_gson_version}"


    val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofit_interceptor_version}"
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso_version}"

    /*circleImageview*/
    val circleimageview = "de.hdodenhof:circleimageview:${Versions.circle_imageview_version}"
    /*palette*/
    val palette = "androidx.palette:palette:${Versions.palette_version}"

    /*Dagger*/
    val dagger = "com.google.dagger:dagger:${Versions.dagger2_version}"

    /*kapt Dagger*/
    val Dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2_version}"

    /*caligraphy*/
    val caligraphy = "uk.co.chrisjenx:calligraphy:${Versions.caligraphy_version}"

    /*materialEditext*/
    val materialEditext = "com.rengwuxian.materialedittext:library:${Versions.material_edittext_version}"

    /*sdp*/
    val sdp = "com.intuit.sdp:sdp-android:${Versions.sdp_version}"


    /*loading-button*/
    val loading_button = "br.com.simplepass:loading-button-android:2.1.0"

    /*runtimePermissions*/
    val runtimePermissions = "com.github.florent37:runtime-permission-kotlin:${Versions.rtp_version}"

}

object Android {
    val buildToolsVersion = "28.0.0"
    val minSdkVersion = 28
    val targetSdkVersion = 28
    val compileSdkVersion = 28
    val applicationId = "development.app.checking"
    val versionCode = 1
    val versionName = "0.1"

    val baseUrl="http://shamlatech.net/android_portal/"
    val api_path="kotlinApps/api/"
}

