plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Required starting with Kotlin 2.0 whenever Compose is enabled.
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.patflow.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.patflow.app"
        // minSdk 26: notification channels, adaptive icons, modern WorkManager/Biometric
        // behavior without compatibility shims (Architecture §10)
        minSdk = 26
        // targetSdk 36: Google Play requirement for new apps from Aug 31, 2026 (Architecture §10)
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0-phase1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- Core / Compose ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // --- Navigation Compose ---
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // --- Hilt ---
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-android-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // --- Room ---
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // --- DataStore (Preferences) ---
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // --- kotlinx-datetime (Architecture §10 — domain layer stays free of java.time) ---
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")

    // --- WorkManager (bill cycle generation, reminders) ---
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    // --- Charts ---
    implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.28")

    // --- Security ---
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    // --- Testing ---
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
