plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.capstone_yogain"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.capstone_yogain"
        minSdk = 21

        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    // CC doesn't work anything the Api not deploy on Google Cloud Platform so I make db with sql lite :)
    //        buildConfigField("String", "KEY", "\"http://127.0.0.1:5000/\"")
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

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // rounded image
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // camera x
    val cameraxVersion = "1.4.0-alpha02"
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    //Livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-service:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //pagingdata
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // tensor flow
    implementation ("org.tensorflow:tensorflow-lite:2.9.0")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.9.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.3.0")

    androidTestImplementation("com.google.truth:truth:1.1.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // work
    implementation("androidx.work:work-runtime-ktx:2.9.0")
}