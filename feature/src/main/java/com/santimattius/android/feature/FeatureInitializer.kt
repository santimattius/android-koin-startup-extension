package com.santimattius.android.feature

import android.content.Context
import com.santimattius.android.koin.startup.KoinStartupExtensionInitializer

class FeatureInitializer : KoinStartupExtensionInitializer<Unit>() {

    override fun create(context: Context) {
        loadLazyModules(featureModule)
    }

}