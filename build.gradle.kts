
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.2")
        classpath(libs.google.services)
        // Add other classpath dependencies here
    }
}

allprojects {
    repositories {
//        google()
//        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
}
