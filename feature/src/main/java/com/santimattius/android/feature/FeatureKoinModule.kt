package com.santimattius.android.feature

import org.koin.dsl.lazyModule

internal val featureModule = lazyModule {
    single { FeatureServices() }
}