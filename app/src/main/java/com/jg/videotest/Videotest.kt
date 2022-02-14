package com.jg.videotest

import android.app.Application
import com.jg.videotest.di.applicationModule
import com.jg.videotest.di.contentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Videotest : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Videotest)
            modules(listOf(applicationModule, contentModule))
        }
    }
}