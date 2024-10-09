plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp").version("2.0.20-1.0.25")
}

android {
    namespace = "com.example.stocks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.stocks"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Material 3 Adaptive Library
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.adaptive.navigation)
    // Retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.moshi.v290)

    // Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi)
    implementation(libs.converter.moshi)
    ksp(libs.moshi.kotlin.codegen)

    // OkHttp logging interceptor
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.junit.ktx)



    // Retrofit for testing
    testImplementation (libs.retrofit)
    testImplementation (libs.converter.gson)

    // Coroutines for testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Moshi for testing
    testImplementation(libs.moshi.kotlin)
    testImplementation(libs.moshi)
    testImplementation(libs.converter.moshi)
    
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test.v160)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}