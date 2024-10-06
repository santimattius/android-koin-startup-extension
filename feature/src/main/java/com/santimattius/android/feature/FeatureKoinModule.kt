package com.santimattius.android.feature

import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.koinModules
import org.koin.dsl.module

class FeatureKoinModule : KoinDefinition {

    override fun modules() = koinModules {
        module(featureModule)
    }
}

private val featureModule = module {
    single { FeatureServices() }
}