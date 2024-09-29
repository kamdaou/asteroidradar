// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    alias(libs.plugins.ksp) apply false
}

buildscript {
    dependencies {
//        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
