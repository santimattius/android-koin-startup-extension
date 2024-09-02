# Koin Android Startup Extension
Koin-startup is a powerful library that significantly extends the use of Koin with App Startup. This tool greatly simplifies the configuration of Koin and its dependencies by providing comprehensive support for handling multiple modules in an application.

# Features

- **Quick Integration:** Facilitates the rapid setup of Koin in Android projects using App Startup.
- **Multi-module Management:** Provides support for efficient management of multiple modules within an application.
- **Dependency Initialization:** Allows for orderly initialization of dependencies through the implementation of `KoinDefinition`.
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
	 implementation "com.santimattius.android:koin-startup:${version}"
}

```

Replace `version` with the version of the library you want to use.

# Usage

The `koin-startup` library is designed to simplify dependency management in Android applications, allowing for easy integration with App Startup. We will use practical examples to illustrate how to implement `koin-startup` in different scenarios, such as the initialization of critical services, efficient management of feature modules, and compatibility with other App Startup initializers.

## App

In the code of our Android application, specifically in our `Application` class, we can define our dependencies as follows:

```kotlin
import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.ModuleScope
import com.santimattius.android.startup.service.CrashTrackerService
import com.santimattius.android.startup.service.AppService
import org.koin.core.module.Module
import org.koin.dsl.module

class MainApplication : Application(), KoinDefinition {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun modules(): List<Module> {
        return listOf(appModule)
    }

    override fun scope() = ModuleScope.APP
}

val appModule = module {
    single { AppService() }
}
```

Similarly to the default Koin setup, but without specifying the `startKoin` function.

### Feature Modules

First, we need to create an implementation of `KoinDefinition` in our feature module.

```kotlin
class FeatureKoinModule : KoinDefinition {

    override fun modules(): List<Module> {
        return listOf(featureModule)
    }
}

private val featureModule = module {
    single { FeatureServices() }
}
```

Similar to what we did for the `Application` class, for `koin-startup` to recognize our implementation of `KoinDefinition` in our module, we must define the `META-INF/services` file. For this, within the resources directory of our feature module, we will set up the following structure:

[Image]

There we will have a file named `com.santimattius.android.koin.startup.KoinDefinition`, which will contain the reference to our implementation of `KoinDefinition`.

[Image]

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
import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.ModuleScope
import com.santimattius.android.startup.service.CrashTrackerService
import com.santimattius.android.startup.service.AppService
import org.koin.core.module.Module
import org.koin.dsl.module

class MainApplication : Application(), KoinDefinition {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun modules(): List<Module> {
        return listOf(appModule)
    }

    override fun scope() = ModuleScope.APP
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

If you have questions, issues, or suggestions regarding this library, feel free to [open a new issue](https://github.com/tuusuario/AndroidXYZ/issues) on GitHub. We are here to help you!