import org.gradle.api.JavaVersion

// Define the versions block
val versions = project.properties

plugins {
    id("com.android.application") version "8.3.2"

}

android {
    namespace = "com.example.e_commerce_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commerce_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0") // Update this to the latest version
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0") // Use the latest version from BoM
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Include dependencies from TOML file
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${versions["lifecycleLivedataKtx"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${versions["lifecycleViewmodelKtx"]}")
    implementation("androidx.navigation:navigation-fragment:${versions["navigationFragment"]}")
    implementation("androidx.navigation:navigation-ui:${versions["navigationUi"]}")

    implementation("com.google.firebase:firebase-firestore:25.0.0") // Use the latest version from BoM
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")

    testImplementation("junit:junit:${versions["junit"]}")
    androidTestImplementation("androidx.test.ext:junit:${versions["junitVersion"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${versions["espressoCore"]}")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation("org.robolectric:robolectric:4.7.3")
    implementation("com.squareup.picasso:picasso:2.8")
}
