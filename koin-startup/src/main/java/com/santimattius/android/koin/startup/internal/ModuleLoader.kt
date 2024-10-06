package com.santimattius.android.koin.startup.internal

import android.util.Log
import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.KoinFeatureExperimental
import org.koin.core.module.Module
import java.util.ServiceLoader

internal class ModuleLoader {

    @OptIn(KoinFeatureExperimental::class)
    fun load(): KoinDefinition = try {
        val providers = readDefinitions()
        val modules = mutableListOf<Module>()
        val lazyModules = mutableListOf<Lazy<Module>>()
        providers.sortedBy { it.scope().value }.forEach {
            val koinModules = it.modules()
            modules.addAll(koinModules.modules)
            lazyModules.addAll(koinModules.lazyModules)
        }
        MergedKoinDefinition(modules, lazyModules)
    } catch (ex: Throwable) {
        Log.e("ModuleLoader", "load: fail load service loader modules", ex)
        DefaultKoinDefinition
    }

    private fun readDefinitions(): MutableList<KoinDefinition> {
        val providers = mutableListOf<KoinDefinition>()
        val loader = ServiceLoader.load(KoinDefinition::class.java, this.javaClass.classLoader)
        for (provider in loader) {
            providers.add(provider)
        }
        return providers
    }
}

