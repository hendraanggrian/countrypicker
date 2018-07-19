import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.kotlin.dsl.kotlin

plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "com.example.countrydialog"
        versionCode = 1
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            assets.srcDirs("assets")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    buildTypes {
        all {
            buildConfigField("String", "RELEASE_WEBSITE", "\"$RELEASE_WEBSITE\"")
            buildConfigField("String", "RELEASE_ARTIFACT", "\"$RELEASE_ARTIFACT\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(project(":$RELEASE_ARTIFACT"))

    implementation(material())
    implementation(androidx("core", "core-ktx"))
    implementation(androidx("appcompat"))
    implementation(androidx("coordinatorlayout"))
    implementation(androidx("preference"))

    implementation(hendraanggrian("material", "errorbar-ktx", VERSION_ANDROIDX))
}