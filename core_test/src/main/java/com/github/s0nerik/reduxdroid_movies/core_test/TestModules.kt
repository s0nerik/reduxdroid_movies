package com.github.s0nerik.reduxdroid_movies.core_test

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid.core.di.combinedReducer
import com.github.s0nerik.reduxdroid.core.di.middlewares
import com.github.s0nerik.reduxdroid.core.middleware.Middleware
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import io.github.classgraph.ClassGraph
import me.tatarka.redux.Reducer
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

private fun appModules(vararg whitelistPackages: String): List<Module> {
    val classGraph = ClassGraph().apply {
        enableAllInfo()
        whitelistPackages("com.github.s0nerik.reduxdroid")
        whitelistPackages(*whitelistPackages)
    }
    val scanResult = classGraph.scan()

    val appModules = scanResult.getSubclasses(AppModule::class.qualifiedName)

    appModules.forEach {
        val kClass = Class.forName(it.name).kotlin
        val instance = kClass.constructors.first().call()
        (instance as AppModule).onCreate()
    }

    return AppModule.registeredModules
}

val appModules by lazy {
    appModules("com.github.s0nerik.reduxdroid_movies")
}

fun testModule(middlewares: () -> List<Middleware<*, *>> = { emptyList() }) = module {
    single(override = true) { TestCoroutineContextHolder as CoroutineContextHolder }
    single { TestMiddleware(get()) }
    combinedReducer() bind Reducer::class
    middlewares {
        middlewares() + get<TestMiddleware>()
    }
    single(override = true) { ApplicationProvider.getApplicationContext<Application>() }
    single(override = true) { ApplicationProvider.getApplicationContext<Context>() }
}
