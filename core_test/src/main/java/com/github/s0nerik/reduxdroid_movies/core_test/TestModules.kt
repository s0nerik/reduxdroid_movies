package com.github.s0nerik.reduxdroid_movies.core_test

import com.github.s0nerik.reduxdroid.core.di.AppModule
import com.github.s0nerik.reduxdroid_movies.core.util.CoroutineContextHolder
import io.github.classgraph.ClassGraph
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

val appModules = appModules("com.github.s0nerik.reduxdroid_movies")
val testModule = module {
    single(override = true) { TestCoroutineContextHolder as CoroutineContextHolder }
}
