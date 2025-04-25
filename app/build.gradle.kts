plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.movietheatre"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movietheatre"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.32:8080/api/\"")
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
        viewBinding = true
        buildConfig = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}




dependencies {
    implementation(project(":core"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":resource"))
    implementation(project(":feature"))
    implementation(project(":feature:login"))
    implementation(project(":feature:login:data"))
    implementation(project(":feature:login:domain"))
    implementation(project(":feature:login:presentation"))
    implementation(project(":feature:register"))
    implementation(project(":feature:register:data"))
    implementation(project(":feature:register:domain"))
    implementation(project(":feature:register:presentation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:home:data"))
    implementation(project(":feature:home:domain"))
    implementation(project(":feature:home:presentation"))
    implementation(project(":feature:movie_detail"))
    implementation(project(":feature:payment"))
    implementation(project(":feature:seat"))
    implementation(project(":feature:movie_detail:data"))
    implementation(project(":feature:movie_detail:domain"))
    implementation(project(":feature:movie_detail:presentation"))
    implementation(project(":feature:payment:data"))
    implementation(project(":feature:payment:domain"))
    implementation(project(":feature:payment:presentation"))
    implementation(project(":feature:seat:data"))
    implementation(project(":feature:seat:domain"))
    implementation(project(":feature:seat:presentation"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:profile:data"))
    implementation(project(":feature:profile:domain"))
    implementation(project(":feature:profile:presentation"))
    implementation(project(":feature:shop"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:shop:data"))
    implementation(project(":feature:shop:domain"))
    implementation(project(":feature:shop:presentation"))
    implementation(project(":feature:splash:data"))
    implementation(project(":feature:splash:domain"))
    implementation(project(":feature:splash:presentation"))
    implementation(project(":feature:movie_quiz"))
    implementation(project(":feature:movie_quiz:data"))
    implementation(project(":feature:movie_quiz:domain"))
    implementation(project(":feature:movie_quiz:presentation"))
    implementation(project(":navigation"))

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}