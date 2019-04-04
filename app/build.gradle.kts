import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument.DefaultArgument.arguments

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    kotlin("kapt")
}
android{
    compileSdkVersion(Android.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        isEnabled = true
    }
    productFlavors {
        create("demo") {

            applicationId = Android.applicationId
        }
        create("production") {
            applicationId =  Android.applicationId
        }
    }
    buildTypes {
        val booleanType = "Boolean"
        val stringType = "String"


        getByName("debug") {
            //buildConfigField(stringType,"SERVER_BASE_URL", Android.baseUrl)
            isDebuggable = true
        }

        create("qa") {

           // buildConfigField(stringType,"SERVER_BASE_URL", Android.baseUrl)

            isShrinkResources = true
            isMinifyEnabled = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("release") {

            //buildConfigField(stringType,"SERVER_BASE_URL", Android.baseUrl)
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
    implementation(Libs.kotlin_std)
    /*androidx*/
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.constraint)

    /*databinding, viewmodel and coroutines*/
    kapt(Libs.databinding)
    implementation(Libs.viewModelExt)
    implementation(Libs.coroutines)

    /*retrofit*/
    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.retrofit2_kotlin_coroutines)
    implementation(Libs.retrofit2_converter_gson)

    /*dagger*/
    implementation(Libs.dagger)
    kapt(LibsDagger.compiler)//kapt


    /*Okhttp3*/
    implementation(Libs.logging_interceptor)

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


    /*implementation fileTree(dir: 'libs', include: ['*.jar'])
    testImplementation "junit:junit:$test_junit_version"
    androidTestImplementation "androidx.test:runner:$runner_version"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espresso_core_version"
    */

}

repositories {
    mavenCentral()
}

