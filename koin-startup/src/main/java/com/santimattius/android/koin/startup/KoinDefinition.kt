package com.santimattius.android.koin.startup

import org.koin.core.module.Module


/**
 * Definition for Koin modules and configurations.
 *
 * This interface allows you to define the modules and their behavior
 * for dependency injection using Koin.
 */
interface KoinDefinition {

    /**
     * Returns a list of modules to be loaded by Koin.
     *
     * These modules define the core dependencies for your application.
     *
     * @return List of [Module] objects.
     */
    fun modules(): List<Module>

    /**
     * Returns a list of lazily initialized modules.
     *
     * Lazy modules are only loaded when they are first accessed,
     * which can improve startup performance.
     *
     * @return List of [Lazy] loaded [Module] objects.
     */
    fun lazyModules(): List<Lazy<Module>> = emptyList()

    /**
     * Defines the scope for the modules.
     *
     * This function is experimental and might change in future releases.
     *
     * @return The [ModuleScope] for the modules.
     */
    @KoinFeatureExperimental
    fun scope() = ModuleScope.NONE
}

