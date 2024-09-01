package com.santimattius.android.startup

import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinDefinition
import com.santimattius.android.koin.startup.ModuleScope
import com.santimattius.android.startup.service.CrashTrackerService
import com.santimattius.android.startup.service.AppService
import org.koin.core.module.Module
import org.koin.dsl.module

class MainApplication : Application(), KoinDefinition {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun modules(): List<Module> {
        return listOf(appModule)
    }

    override fun scope() = ModuleScope.APP
}

val appModule = module {
    single { AppService() }
    single { CrashTrackerService() }
}