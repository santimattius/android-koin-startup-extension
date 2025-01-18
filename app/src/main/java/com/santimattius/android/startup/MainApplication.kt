package com.santimattius.android.startup

import android.app.Application
import android.util.Log
import com.santimattius.android.koin.startup.KoinStartupExtension
import com.santimattius.android.startup.service.AppService
import com.santimattius.android.startup.service.CrashTrackerService
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartupExtension {

    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.simpleName, "onCreate: application created")
        KoinPlatformTools.defaultContext().get()
    }

    override fun onKoinStartup(): KoinConfiguration {
        return KoinConfiguration {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    single { AppService() }
    single { CrashTrackerService() }
}