plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization) // necesario para rutas @Serializable
    alias(libs.plugins.ksp)                  // KSP para Room
}

android {
    namespace = "com.example.lab08"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.lab08"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release { isMinifyEnabled = false }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    buildFeatures { compose = true }
}

dependencies {
    // -------- Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // -------- AndroidX base
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // -------- Lifecycle + ViewModel (Compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // -------- Navigation Compose (soporta rutas @Serializable)
    implementation(libs.androidx.compose.navigation)

    // -------- DataStore (Preferences)
    implementation(libs.androidx.datastore.preferences)

    // -------- Coroutines (requerido por DataStore/Room/VM)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // -------- Room + KSP (source of truth)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // -------- Kotlinx Serialization (para las rutas tipadas)
    implementation(libs.kotlin.serialization)

    // -------- Compose UI + Material3
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material.icons.extended)

    // -------- Imágenes
    implementation(libs.coil.compose)

    // -------- Test/Debug
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

// (opcional pero útil para inspeccionar esquemas de Room)
// ksp {
//     arg("room.schemaLocation", "$projectDir/schemas")
// }
