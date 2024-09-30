package com.santimattius.android.startup

import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinDeclaration
import com.santimattius.android.startup.service.AppService
import com.santimattius.android.startup.service.CrashTrackerService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

class MainApplication : Application(), KoinDeclaration {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
    }

    override fun declaration(): KoinAppDeclaration {
        return {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    single { AppService() }
    single { CrashTrackerService() }
}