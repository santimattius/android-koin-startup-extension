plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.mavenPublish)
}

val androidMinSdkVersion: String by project
val androidTargetSdkVersion: String by project


val libraryGroupId: String by project
val libraryArtifactId: String by project
val libraryVersion: String by project

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.santimattius.android.koin.startup"
    compileSdk = androidTargetSdkVersion.toInt()

    defaultConfig {
        minSdk = androidMinSdkVersion.toInt()

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
    lint {
        disable += "EnsureInitializerMetadata"
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
    api(libs.koin.androidx.startup)
    api(libs.startup.android)
}


mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(libraryGroupId, libraryArtifactId, libraryVersion)

    pom {
        name = "Koin Android Startup Extension"
        description =
            "Koin Startup Extension is a powerful library that significantly extends the use of Koin with App Startup."
        inceptionYear = "2025"
        url = "https://github.com/santimattius/android-koin-startup-extension"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "santiago-mattiauda"
                name = "Santiago Mattiauda"
                url = "https://github.com/santimattius"
            }
        }
        scm {
            url = "https://github.com/santimattius/android-koin-startup-extension/"
            connection =
                "scm:git:git://github.com/santimattius/android-koin-startup-extension.git"
            developerConnection =
                "scm:git:ssh://git@github.com/santimattius/android-koin-startup-extension.git"
        }
    }
}