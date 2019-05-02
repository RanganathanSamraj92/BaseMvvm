import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenLocal()
        google()
        jcenter()
        // Add repository
        maven ( url = "https://maven.fabric.io/public")

    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")
        classpath("com.google.gms:google-services:4.2.0")
        classpath("io.fabric.tools:gradle:1.26.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }

    allprojects {
        repositories {
            mavenLocal()
            google()
            jcenter()
            maven (url = "https://jitpack.io")
            maven (url ="https://maven.google.com" )
        }
    }

}


/*task clean(type: Delete) {
    delete rootProject.buildDir
}*/
