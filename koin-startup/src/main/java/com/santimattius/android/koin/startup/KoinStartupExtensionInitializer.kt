package com.santimattius.android.koin.startup

import android.content.Context
import androidx.startup.Initializer
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androix.startup.KoinInitializer
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.coroutine.KoinCoroutinesEngine
import org.koin.core.coroutine.KoinCoroutinesEngine.Companion.EXTENSION_NAME
import org.koin.core.extension.coroutinesEngine
import org.koin.core.module.Module
import org.koin.mp.KoinPlatformTools

class KoinStartupExtensionInitializer : Initializer<Unit> {

    override fun create(context: Context) {}

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(KoinInitializer::class.java)
    }
}

@OptIn(KoinInternalApi::class)
fun Initializer<Unit>.loadLazyModules(vararg moduleList: Lazy<Module>, dispatcher: CoroutineDispatcher? = null) {
    val koin = KoinPlatformTools.defaultContext().get()
    with(koin) {
        setCoroutinesEngine(dispatcher)
        koin.coroutinesEngine.launchStartJob {
            koin.loadModules(moduleList.map { it.value })
        }
    }
}

@OptIn(KoinInternalApi::class)
internal fun Koin.setCoroutinesEngine(dispatcher: CoroutineDispatcher? = null) {
    with(extensionManager) {
        if (getExtensionOrNull<KoinCoroutinesEngine>(EXTENSION_NAME) == null) {
            registerExtension(EXTENSION_NAME, KoinCoroutinesEngine(dispatcher))
        }
    }
}