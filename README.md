![image - Page 2(1)](https://github.com/user-attachments/assets/ae2e57e4-570b-486d-a175-8ecc7d85863d)

[![Latest Release](https://maven-badges.sml.io/sonatype-central/io.github.santimattius.android/koin-startup-extension/badge.svg?subject=Latest%20Release&color=blue)](https://maven-badges.sml.io/sonatype-central/io.github.santimattius.android/koin-startup-extension/)

# Koin Android Startup Extension
Koin-startup-extension is a powerful library that significantly extends the use of Koin with App Startup. This tool greatly simplifies the configuration of Koin and its dependencies by providing comprehensive support for handling multiple modules in an application.

# Features

- **Quick Integration:** Facilitates the rapid setup of Koin in Android projects using App Startup.
- **Multi-module Management:** Provides support for efficient management of multiple modules within an application.
- **Compatibility with Initializers:** Compatible with other App Startup initializers for smoother integration.
- **Flexibility in Module Definition:** Offers flexibility to define modules both in the main application and feature modules.

# Installation

You can add this library to your Android project using Gradle. Make sure to include the repository in your project-level `build.gradle` file:

```groovy
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```

Then, add the dependency in your `build.gradle` file at the application level:

```groovy
dependencies {
   implementation "	io.github.santimattius.android:koin-startup-extension:${version}"
}

```

Replace `version` with the version of the library you want to use.

# Usage

The `koin-startup-extension` library is designed to simplify dependency management in Android applications, allowing for easy integration with App Startup. 
We will use practical examples to illustrate how to implement `koin-startup-extension` in different scenarios, such as the initialization of critical services, efficient management of feature modules, and compatibility with other App Startup initializers.

## App

In the code of our Android application, specifically in our `Application` class, we can define our dependencies as follows:

```kotlin
import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinStartupExtension
import com.santimattius.android.startup.service.AppService
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartupExtension {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun onKoinStartup(): KoinConfiguration {
        return KoinConfiguration {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    single { AppService() }
}
```

Similarly to the default Koin setup, but without specifying the `startKoin` function.

### Feature Modules

First, we need to create an implementation of `KoinStartupExtensionInitializer` in our feature module.

```kotlin
import android.content.Context
import com.santimattius.android.koin.startup.KoinStartupExtensionInitializer

class FeatureInitializer : KoinStartupExtensionInitializer<Unit>() {

    override fun create(context: Context) {
        loadLazyModules(featureModule)
    }

}
```

## Support for Other App Startup Initializers

If we need to initialize some dependencies both in our application and in our feature module, we must create an Initializer that depends on `KoinInitializer`.

### Let's see an example

Suppose our `CrashTrackerService` needs to be initialized before being used.

```kotlin
class CrashTrackerService {

    var isInitialized: Boolean = false
        private set

    fun initialize(context: Context): CrashTrackerService {
        isInitialized = true
        return this
    }
}
```

For this, it is necessary to execute the `initialize` function. For example, we will define this as a dependency in the application module.

```kotlin
@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartupExtension {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun onKoinStartup(): KoinConfiguration {
        return KoinConfiguration {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    single { AppService() }
    single { CrashTrackerService() }
}
```

And then we will create our initializer for `CrashTrackerService`

```kotlin
class CrashTrackerInitializer : Initializer<Unit>, KoinComponent {

    private val crashTrackerService: CrashTrackerService by inject()

    override fun create(context: Context) {
        Log.i(this::class.simpleName, "create: CrashTrackerService created")
        crashTrackerService.initialize(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(KoinInitializer::class.java)
    }

}
```

This implements the `KoinComponent` interface to inject `CrashTrackerService`. In the `create` method, it executes the `initialize` function of our service.

# Contributions

Contributions are welcome! If you want to contribute to this library, please follow these steps:

1. Fork the repository.
2. Create a new branch for your contribution (`git checkout -b feature/new-feature`).
3. Make your changes and ensure you follow the style guides and coding conventions.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push your changes to your GitHub repository (`git push origin feature/new-feature`).
6. Create a new pull request and describe your changes in detail.

## Contact

If you have questions, issues, or suggestions regarding this library, feel free to [open a new issue](https://github.com/santimattius/android-koin-startup-extension/issues) on GitHub. We are here to help you!
