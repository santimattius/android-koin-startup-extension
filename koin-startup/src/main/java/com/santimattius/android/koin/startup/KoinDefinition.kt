package com.santimattius.android.koin.startup


/**
 * Definition for Koin modules and configurations.
 *
 * This interface allows you to define the modules and their behavior
 * for dependency injection using Koin.
 */
interface KoinDefinition {

    fun modules(): KoinModules

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

interface KoinAppDefinition : KoinDefinition {

    @OptIn(KoinFeatureExperimental::class)
    override fun scope(): ModuleScope {
        return ModuleScope.APP
    }
}

