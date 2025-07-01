package com.example.iisc_assignment


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class annotated with @HiltAndroidApp to enable Hilt dependency injection.
 *
 * - Required for Hilt to generate necessary components and perform dependency injection.
 * - Acts as the entry point for the app's global initialization logic (if any).
 */
@HiltAndroidApp
class MyApp : Application()
