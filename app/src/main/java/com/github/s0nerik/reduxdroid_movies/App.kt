package com.github.s0nerik.reduxdroid_movies

import android.app.Application
import com.github.s0nerik.reduxdroid.core.di.AppModule
import me.tatarka.redux.android.lifecycle.LiveDataAdapter
import org.joda.time.DateTime
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LiveDataAdapter.setDebugAll(true)
        startKoin(this, AppModule.registeredModules, logger = EmptyLogger())

        // First DateTime creation is slow, so let's do it here.
        DateTime.now()
    }
}