package com.santimattius.android.koin.startup.internal

import com.santimattius.android.koin.startup.KoinDefinition
import org.koin.core.module.Module

internal class MergedKoinDefinition(
    private val modules: List<Module>,
    private val lazyModules: List<Lazy<Module>>
) : KoinDefinition {

    override fun modules() = modules

    override fun lazyModules() = lazyModules
}