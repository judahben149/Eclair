package com.judahben149.eclair

import android.app.Application
import com.judahben149.eclair.di.platformModule
import com.judahben149.eclair.di.sharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApplication: Application() {

    val androidModules = listOf(platformModule(this))

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(sharedModules + androidModules)

            androidContext(this@AndroidApplication)
        }
    }
}