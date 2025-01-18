package com.santimattius.android.feature

import org.koin.dsl.lazyModule

val featureModule = lazyModule {
    single { FeatureServices() }
}