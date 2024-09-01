package com.santimattius.android.koin.startup.internal

import com.santimattius.android.koin.startup.KoinDefinition
import org.koin.core.module.Module

internal object DefaultKoinDefinition : KoinDefinition {

    override fun modules() = emptyList<Module>()

    override fun lazyModules() = emptyList<Lazy<Module>>()
}