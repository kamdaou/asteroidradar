import java.io.FileInputStream
import java.util.Properties

plugins {
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val envPropertiesFile: File = rootProject.file("env.properties")
val envProperties = Properties()
envProperties.load(envPropertiesFile?.let { FileInputStream(it) })

android {
    namespace = "com.ampersand.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField(
            "String",
            "API_KEY",
            envProperties["API_KEY"]?.toString() ?: ""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.bundles.retrofit)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}