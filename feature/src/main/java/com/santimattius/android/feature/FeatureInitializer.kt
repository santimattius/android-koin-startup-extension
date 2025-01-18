package com.santimattius.android.feature

import android.content.Context
import androidx.startup.Initializer
import com.santimattius.android.koin.startup.KoinStartupExtensionInitializer
import com.santimattius.android.koin.startup.loadLazyModules

class FeatureInitializer : Initializer<Unit> by KoinStartupExtensionInitializer() {

    override fun create(context: Context) {
        loadLazyModules(featureModule)
    }
}