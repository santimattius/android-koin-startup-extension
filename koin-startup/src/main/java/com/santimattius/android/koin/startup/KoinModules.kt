package com.santimattius.android.koin.startup

import org.koin.core.module.Module

data class KoinModules(
    internal var modules: List<Module> = emptyList(),
    internal var lazyModules: List<Lazy<Module>> = emptyList(),
) {

    fun module(module: Module) {
        this.modules += this.modules + module
    }

    fun modules(modules: List<Module>) {
        this.modules += this.modules + modules
    }

    fun lazyModules(lazyModules: List<Lazy<Module>>) {
        this.lazyModules += this.lazyModules + lazyModules
    }
}

fun koinModules(block: KoinModules.() -> Unit): KoinModules {
    val koinModules = KoinModules()
    koinModules.block()
    return koinModules
}