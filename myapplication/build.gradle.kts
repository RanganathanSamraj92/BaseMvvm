plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
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

        }
        create("production") {
        }
    }
    buildTypes {

        getByName("debug") {
            resValue("string", "app_name", AppConfig.BaseAppTitle)
            isDebuggable = true
        }

        create("qa") {
            isMinifyEnabled = true
            isUseProguard = true
            resValue("string", "app_type", "Debug")
            resValue("string", "app_name", AppConfig.BaseAppTitle)
        }

        getByName("release") {

            resValue("string", "app_name", AppConfig.BaseAppTitle)

            isMinifyEnabled = true
            isUseProguard = true
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
    implementation(Libs.material)
    implementation(Libs.Support.constraint)
    /*runtimePermissions*/
    implementation(Libs.runtimePermissions)
}

repositories {
    mavenCentral()
}
apply(mapOf("plugin" to "com.google.gms.google-services"))

