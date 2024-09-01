package com.santimattius.android.koin.startup.internal

import android.util.Log
import com.santimattius.android.koin.startup.KoinDefinition
import org.koin.core.module.Module
import java.util.ServiceLoader

internal class ModuleLoader {

    fun load(): KoinDefinition = try {
        val providers = mutableListOf<KoinDefinition>()
        val loader = ServiceLoader.load(KoinDefinition::class.java, this.javaClass.classLoader)
        for (provider in loader) {
            providers.add(provider)
        }
        val modules = mutableListOf<Module>()
        val lazyModules = mutableListOf<Lazy<Module>>()
        providers.sortedBy {
            it.scope().value
        }.forEach {
            modules.addAll(it.modules())
            lazyModules.addAll(it.lazyModules())
        }
        MergedKoinDefinition(modules, lazyModules)
    } catch (ex: Throwable) {
        Log.e("ModuleLoader", "load: fail load service loader modules", ex)
        DefaultKoinDefinition
    }
}

