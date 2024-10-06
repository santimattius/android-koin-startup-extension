package com.santimattius.android.startup

import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinAppDefinition
import com.santimattius.android.koin.startup.koinModules
import com.santimattius.android.startup.service.AppService
import com.santimattius.android.startup.service.CrashTrackerService
import org.koin.dsl.module

class MainApplication : Application(), KoinAppDefinition {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun modules() = koinModules {
        module(appModule)
    }
}

val appModule = module {
    single { AppService() }
    single { CrashTrackerService() }
}