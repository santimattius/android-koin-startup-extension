package com.santimattius.android.feature

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.santimattius.android.koin.startup.KoinStartupExtensionInitializer
import com.santimattius.android.koin.startup.loadLazyModules

class FeatureInitializer : Initializer<Unit> by KoinStartupExtensionInitializer() {

    override fun create(context: Context) {
        Log.i("FeatureInitializer", "create:FeatureInitializer ")
        loadLazyModules(featureModule)
    }
}