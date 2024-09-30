package com.santimattius.android.koin.startup

import org.koin.dsl.KoinAppDeclaration

interface KoinDeclaration {

    fun declaration(): KoinAppDeclaration
}