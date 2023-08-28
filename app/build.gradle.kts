plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.santosfabi.catgallery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.santosfabi.catgallery"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "IMGUR_CLIENT_ID", "\"1ceddedc03a5d71\"")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    android.buildFeatures.buildConfig = true

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    val hilt = "2.45"
    //Hilt
    implementation("com.google.dagger:hilt-android:2.$hilt")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    // kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("com.google.dagger:hilt-android-testing:$hilt")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt")
    kaptTest("com.google.dagger:hilt-android-compiler:$hilt")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hilt")
    implementation ("com.google.dagger:hilt-android:$hilt")
    kapt ("com.google.dagger:hilt-android-compiler:$hilt")

    // Retrofit
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Unit test -- Robolectric environment -- Mockito framework
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.mockito:mockito-inline:5.2.0")

    //Instrumented test
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.fragment:fragment-testing:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.6.1")


    debugImplementation("androidx.fragment:fragment-testing:1.7.0-alpha03")
    debugImplementation("androidx.fragment:fragment-ktx:1.7.0-alpha03")
    debugImplementation("androidx.test:core:1.5.0")
    debugImplementation("androidx.test:rules:1.5.0")
    debugImplementation("androidx.test:runner:1.5.2")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.47")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.47")

    testImplementation ("org.robolectric:robolectric:4.10.3")

}


kapt {
    correctErrorTypes = true
}