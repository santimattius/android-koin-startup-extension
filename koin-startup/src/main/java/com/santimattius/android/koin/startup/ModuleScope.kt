package com.santimattius.android.koin.startup

/**
 * Defines the scope of a Koin module.
 *
 * This enum is used to specify the scope of dependencies within a Koin module.
 * Different scopes can be used to control the lifecycle and visibility of dependencies.
 *
 * **Note:** This enum is experimental and might change in future releases.
 *
 * @property value The integer value associated with the scope.
 */
@KoinFeatureExperimental
enum class ModuleScope(val value: Int) {
    /**
     * Application-level scope. Dependencies in this scope exist for the entire lifetime of the application.
     */
    APP(0),

    /**
     * Core scope. Dependencies in this scope are essential for the core functionality of the application.
     */
    CORE(1),

    /**
     * Library scope. Dependencies in this scope are related to external libraries.
     */
    LIBRARY(2),

    /**
     * Feature scope. Dependencies in this scope are specific to a particular feature or module within the application.
     */
    FEATURE(3),

    /**
     * No scope. Dependencies in this scope have no specific scope defined.
     */
    NONE(4),
}