package com.santimattius.android.koin.startup.internal

import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.KoinModules

internal object DefaultKoinDefinition : KoinDefinition {

    override fun modules() = KoinModules()
}