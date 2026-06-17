plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.chuyou.base"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        getByName("main") {
            jniLibs.setSrcDirs(listOf("libs"))
            res.setSrcDirs(
                listOf(
                    "src/main/res",
                    "src/main/res-sw",
                )
            )
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)

    api(platform(libs.androidx.compose.bom))
    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.test.manifest)

    api(libs.androidx.compose.material.icons.extended)
    api(libs.bundles.compose)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.browser)
    api(libs.coil.compose)
    api(libs.androidx.paging.compose)
    api(libs.androidx.paging.runtime)
    api(libs.paging.common)
    api(libs.mmkv)
}
