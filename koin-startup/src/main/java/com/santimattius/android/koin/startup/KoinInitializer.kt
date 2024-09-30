package com.santimattius.android.koin.startup

import android.content.Context
import androidx.startup.Initializer
import com.santimattius.android.koin.startup.internal.ModuleLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinExperimentalAPI
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
        val (syncModules, lazyModules) = extractModules(context)
        startKoin {
            androidContext(context)
            //TODO:next version set this from config
            androidLogger(Level.INFO)
            allowOverride(false)
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
     * @param context The application context.
     * @return A pair of module lists: eagerly loaded modules and lazily loaded modules.
     */
    private fun extractModules(context: Context): ApplicationModules {
        val featureModules = moduleLoader.load()
        return if (context is KoinDefinition) {
            val mergedModules = context.modules() + featureModules.modules()
            val mergedLazyModules = context.lazyModules() + featureModules.lazyModules()
            mergedModules to mergedLazyModules
        } else {
            featureModules.modules() to featureModules.lazyModules()
        }
    }
}