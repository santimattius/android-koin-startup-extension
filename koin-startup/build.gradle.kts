plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    id("maven-publish")
}

android {
    namespace = "com.santimattius.android.koin.startup"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    api(platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.coroutine)
    api(libs.koin.android)

    api(libs.startup.android)
}

fun extraString(key: String): String {
    return extra[key] as String
}

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = extraString("group_id")
                artifactId = extraString("artifact_id")
                version = extraString("lib_version")
            }
        }
    }
}