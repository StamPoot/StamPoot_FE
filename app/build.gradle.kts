import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/sangillee/androidKeyStore")
            storePassword = "kd01933892@@"
            keyAlias = "20240528_FootStamp"
            keyPassword = "kd01933892@@"
        }
    }
    namespace = "com.example.footstamp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.footstamp"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://impine.shop/\""
        )
        buildConfigField(
            "String",
            "GOOGLE_BASE_URL",
            "\"https://impine.shop/login/oauth2/code/google/\""
        )
        buildConfigField(
            "String",
            "KAKAO_REDIRECT_URL",
            "\"https://impine.shop/login/oauth2/code/kakao\""
        )
        buildConfigField(
            "String",
            "KAKAO_BASE_URL",
            "\"https://kauth.kakao.com/oauth/\""
        )
        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            "\"59599924227-masr58k95rljkkfa6oglphbiufnadatc.apps.googleusercontent.com\""
        )
        buildConfigField("String", "GOOGLE_CLIENT_SECRET", "\"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\"")
        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            "\"c17162ddfe7a97800cc572a4c27a4f4b\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("release")
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            sourceSets {
                getByName("main") {
                    java.srcDir(File("build/generated/ksp/debug/kotlin"))
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3-android:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.code.gson:gson:2.9.0")

    // Compose Dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    ksp("com.google.dagger:hilt-android-compiler:2.48")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Kotlin extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Google Login
    implementation("androidx.credentials:credentials:1.2.1")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.1")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    // Kakao Login
    implementation("com.kakao.sdk:v2-all:2.20.1")
}