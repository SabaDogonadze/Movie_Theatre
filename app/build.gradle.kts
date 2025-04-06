plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.0"
    alias(libs.plugins.safeargs)
    id("com.google.gms.google-services")
    id("com.google.protobuf") version "0.9.4"

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
        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.0.5:8080/api/\"")
        }
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
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.29.0"
    }
    generateProtoTasks {
        all().configureEach {
            plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}



dependencies {

    // network
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // glide
    implementation(libs.glide)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)

    //okhttp

    implementation(libs.logging.interceptor)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //navigation ui
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //flexbox
    implementation(libs.flexbox)

    //protobuf
    implementation (libs.protobuf.javalite)
    implementation(libs.androidx.datastore)

    //google pay
    implementation(libs.play.services.wallet)

}

kapt {
    correctErrorTypes =  true
}
