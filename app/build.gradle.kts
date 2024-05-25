import org.gradle.api.JavaVersion

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.12.0") // Update this to the latest version
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.firebase:firebase-auth:23.0.0") // Use the latest version from BoM
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0") // Use the latest version from BoM
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    //implementation(libs.swiperefreshlayout)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //implementation(libs.swiperefreshlayout)
    
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test:core:1.4.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    androidTestImplementation("org.mockito:mockito-android:4.0.0")

    implementation("androidx.test.espresso:espresso-intents:3.4.0")

    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation("org.robolectric:robolectric:4.7.3")

    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.squareup.picasso:picasso:2.8")

    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
}
