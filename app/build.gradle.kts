
plugins {
    id("com.android.application")
    id("io.fabric")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        isEnabled = true
    }
    productFlavors {
        create("demo") {

            applicationId = AppConfig.applicationId
        }
        create("production") {
            applicationId = AppConfig.applicationId
        }
    }
    buildTypes {

        getByName("debug") {
            resValue("string", "app_name", AppConfig.applicaionName)
            isDebuggable = true
        }

        create("qa") {
            isShrinkResources = true
            isMinifyEnabled = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            resValue("string", "app_type", "Debug")
            resValue("string", "app_name", AppConfig.applicaionName)
        }

        getByName("release") {

            resValue("string", "app_name", AppConfig.applicaionName)

            isShrinkResources = true
            isMinifyEnabled = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }


    }
    /*compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }*/
}




dependencies {
    /*kotlin*/
    implementation(Libs.Kotlin.kotlin_std)
    /*androidx*/
    implementation(Libs.Support.appcompat)
    implementation(Libs.Support.recyclerview)
    implementation(Libs.material)
    implementation(Libs.Support.constraint)
    /*databinding, viewmodel and coroutines*/
    kapt(Libs.databinding)

    implementation(Libs.viewModelExt)
    implementation(Libs.coroutines)
    /*retrofit*/
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converter_moshi)
    implementation(Libs.Retrofit.retrofit2_kotlin_coroutines)
    implementation(Libs.Retrofit.retrofit2_converter_gson)
    implementation(Libs.Retrofit.logging_interceptor)

    /*firebase*/
    implementation(Libs.Firebase.auth)
    implementation(Libs.Firebase.messaging)
    implementation(Libs.Firebase.database)
    implementation(Libs.Firebase.storage)
    implementation(Libs.Firebase.remote_config)
    implementation(Libs.Firebase.firebase_core)
    implementation(Libs.Firebase.firestore)
    /*crashlytics*/
    implementation(Libs.Firebase.crashlytics)

    /*dagger*/
    implementation(Libs.Dagger.core)
    kapt(Libs.Dagger.compiler)
    /*Picasso*/
    implementation(Libs.picasso)
    /*circleImageview*/
    implementation(Libs.circleimageview)
    /*palette*/
    implementation(Libs.palette)
    /*caligraphy*/
    implementation(Libs.caligraphy)
    /*materialEditext*/
    implementation(Libs.materialEditext)
    /*sdp*/
    implementation(Libs.sdp)
    /*loading_button*/
    implementation(Libs.loading_button)
    /*runtimePermissions*/
    implementation(Libs.runtimePermissions)
    implementation ("com.github.mukeshsolanki:android-otpview-pinview:2.0.3")
    implementation(Libs.lottie)

    implementation(project(":myapplication"))




    /*implementation fileTree(dir: 'libs', include: ['*.jar'])
    testImplementation "junit:junit:$test_junit_version"
    androidTestImplementation "androidx.test:runner:$runner_version"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espresso_core_version"
    */

}

repositories {
    mavenCentral()
}

apply(mapOf("plugin" to "com.google.gms.google-services"))


