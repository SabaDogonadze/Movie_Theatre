plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf") version "0.9.4"
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.feature.payment.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":feature:payment:domain"))

    // network
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.datastore.preferences)
    //protobuf
    implementation(libs.protobuf.javalite)
    implementation(libs.androidx.datastore)

    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}

kapt {
    correctErrorTypes = true
}
