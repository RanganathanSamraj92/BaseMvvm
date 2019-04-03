import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}
android{
    compileSdkVersion(Android.compileSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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

    //kapt(Libs.databinding)

    /*viewmodel and coroutines*/
    implementation(Libs.viewModelExt)
    implementation(Libs.coroutines)

    /*retrofit*/
    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.retrofit2_kotlin_coroutines)
    implementation(Libs.retrofit2_converter_gson)

    /*dagger*/
    implementation(Libs.dagger)
    implementation(Libs.Dagger_compiler)//kapt


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
