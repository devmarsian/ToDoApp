plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.testtask.testtasktodo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.testtask.testtasktodo"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {

        correctErrorTypes = true
        useBuildCache = true
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    kapt ("com.github.moxy-community:moxy-compiler:2.2.2")
    implementation ("com.github.moxy-community:moxy-androidx:2.2.2")
    implementation ("io.insert-koin:koin-android:3.4.3")
    implementation ("com.google.dagger:dagger:2.48.1")
    kapt ("com.google.dagger:dagger-compiler:2.48.1")
    implementation ("com.google.dagger:dagger-android:2.11")
    implementation ("com.google.dagger:dagger-android-support:2.11")
    implementation ("javax.inject:javax.inject:1")
    compileOnly ("javax.annotation:jsr250-api:1.0")
//    implementation("com.android.tools.build:gradle:7.0.4")
//    implementation("com.android.tools.build:gradle-api:7.0.4")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10") not needed and cause exceptions upon build
    implementation("com.squareup:javapoet:1.13.0")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation ("androidx.room:room-rxjava3:2.6.1")


}