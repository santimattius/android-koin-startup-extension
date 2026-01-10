package com.santimattius.android.koin.startup

import androidx.startup.Initializer
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androix.startup.KoinInitializer
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.coroutine.KoinCoroutinesEngine
import org.koin.core.coroutine.KoinCoroutinesEngine.Companion.EXTENSION_NAME
import org.koin.core.extension.coroutinesEngine
import org.koin.core.module.Module
import org.koin.mp.KoinPlatformTools

/**
 * [KoinStartupExtensionInitializer]
 *
 * Abstract base class for initializers that integrate with Koin.
 * This class extends [Initializer] and provides a way to lazily load Koin modules.
 *
 * This class defines the following properties:
 * - [dependencies] returns a list of dependency initializers. It is set with [KoinInitializer] class.
 * - [loadLazyModules] method allows you to load modules in Koin in a deferred manner.
 * - [KoinInternalApi] is used to tell that the function uses an internal Koin api.
 *
 * @param T The type of the object initialized by this initializer.
 */
abstract class KoinStartupExtensionInitializer<T> : Initializer<T> {

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return mutableListOf(KoinInitializer::class.java) + dependOn()
    }

    open fun dependOn(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    /**
     * Loads a list of modules lazily into the Koin container.
     *
     * This function allows for deferred loading of modules until they are actually needed.
     * This can be useful for improving startup time and reducing memory footprint.
     *
     * @param moduleList A list of [Lazy] modules to be loaded. Each [Lazy] instance should hold
     *   a [Module] that will be resolved when the lazy module loading is triggered.
     * @param dispatcher An optional [CoroutineDispatcher] that can be used for the coroutines
     *   engine when loading modules. If null, the default dispatcher of the coroutines engine
     *   will be used.
     *
     * @optin KoinInternalApi This function uses internal Koin APIs.
     *
     * Example usage:
     *
     * ```kotlin
     * val myModule = lazy { MyModule() }
     * loadLazyModules(myModule)
     * ```
     */
    @OptIn(KoinInternalApi::class)
    protected fun loadLazyModules(
        vararg moduleList: Lazy<Module>,
        dispatcher: CoroutineDispatcher? = null
    ) {
        val koin = KoinPlatformTools.defaultContext().get()
        with(koin) {
            setCoroutinesEngine(dispatcher)
            koin.coroutinesEngine.launchStartJob {
                koin.loadModules(moduleList.map { it.value })
            }
        }
    }
}

/**
 * Sets the coroutines engine for Koin.
 *
 * This function allows configuring a custom [CoroutineDispatcher] to be used for
 * asynchronous operations within Koin. If no dispatcher is provided, a default
 * dispatcher will be used by the [KoinCoroutinesEngine].
 *
 * The engine is registered as a Koin extension, allowing Koin to manage
 * asynchronous tasks using the specified dispatcher. If an engine is already
 * registered, this call has no effect.
 *
 * @param dispatcher The [CoroutineDispatcher] to use for Koin asynchronous operations.
 *                   If `null`, a default dispatcher is used.
 */
@OptIn(KoinInternalApi::class)
internal fun Koin.setCoroutinesEngine(dispatcher: CoroutineDispatcher? = null) {
    with(extensionManager) {
        if (getExtensionOrNull<KoinCoroutinesEngine>(EXTENSION_NAME) == null) {
            registerExtension(EXTENSION_NAME, KoinCoroutinesEngine(dispatcher))
        }
    }
}