package com.santimattius.android.koin.startup

import android.content.Context
import androidx.startup.Initializer
import com.santimattius.android.koin.startup.internal.ModuleLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.koin.core.logger.Level
import org.koin.core.module.Module

private typealias ApplicationModules = Pair<List<Module>, List<Lazy<Module>>>

/**
 * Koin initializer for Android applications.
 *
 * This class is responsible for initializing the Koin dependency injection framework
 * during the application startup process. It loads modules from various sources,
 * including the application class and feature modules, and configures the Koin instance.
 */
class KoinInitializer : Initializer<Unit> {

    private val moduleLoader = ModuleLoader()

    /**
     * Initializes Koin with the provided application context.
     *
     * This method loads modules, configures the Koin instance, and starts Koin.
     *
     * @param context The application context.
     */
    override fun create(context: Context) {
        if (context !is KoinAppDefinition) {
            error("The application context must implement KoinAppDefinition")
        }

        val (syncModules, lazyModules) = extractModules(context)
        startKoin {
            androidContext(context)
            androidLogger(Level.INFO)
            allowOverride(override = false)
            modules(syncModules)
            lazyModules(lazyModules)
        }
    }

    /**
     * Specifies that this initializer has no dependencies.
     *
     * @return An empty list of initializer classes.
     */
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    /**
     * Extracts Koin modules from the application context and feature modules.
     *
     * @param koinAppDefinition The application context implementing KoinAppDefinition.
     * @return A pair of module lists: eagerly loaded modules and lazily loaded modules.
     */
    private fun extractModules(koinAppDefinition: KoinAppDefinition): ApplicationModules {
        val librariesModules = moduleLoader.load()
        val (appModules, appLazyModules) = koinAppDefinition.modules()
        val (featureModules, featureLazyModules) = librariesModules.modules()
        val mergedModules = appModules + featureModules
        val mergedLazyModules = appLazyModules + featureLazyModules
        return mergedModules to mergedLazyModules
    }
}