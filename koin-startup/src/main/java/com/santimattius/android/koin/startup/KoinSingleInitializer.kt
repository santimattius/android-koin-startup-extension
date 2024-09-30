package com.santimattius.android.koin.startup

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.context.startKoin

class KoinSingleInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (context !is KoinDeclaration) {
            error("KoinInitializer can't start Koin configuration. Please implement KoinDeclaration interface in your Application class and call")
        }
        startKoin(context.declaration())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}