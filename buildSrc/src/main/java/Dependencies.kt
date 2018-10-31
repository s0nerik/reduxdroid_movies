import Versions.bindingCollectionAdapter

object Versions {
    val min_sdk = 21
    val compile_sdk = 28
    val target_sdk = compile_sdk

    val kotlin = "1.3.0"
    val kotlin_coroutines = "1.0.0"
    val kotlin_serialization = "0.9.0"

    val objectbox = "2.2.0"

    internal val reduxdroid = "41a123eceb"
    internal val koin = "1.0.1"
    internal val androidx = "1.0.0"
    internal val androidx_nav = "1.0.0-alpha06"
    internal val androidx_lifecycle = "2.0.0"
    internal val bindingCollectionAdapter = "a19e5af669"
    internal val anko = "0.10.7-rc13"
    internal val facebook = "4.38.0"
    internal val retrofit = "2.4.0"
}

object Libs {
    //region Kotlin
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    val kotlin_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlin_serialization}"

    val kotlin = arrayOf(kotlin_stdlib, kotlin_reflect, kotlin_coroutines, kotlin_serialization)
    //endregion

    //region Utils
    val anko_commons = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    //endregion

    //region Koin
    val koin_core = "org.koin:koin-core:${Versions.koin}"
    val koin_android = "org.koin:koin-android:${Versions.koin}"
    val koin_androidx_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koin_androidx_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val koin = arrayOf(koin_core, koin_android, koin_androidx_scope, koin_androidx_viewmodel)
    //endregion

    //region Reduxdroid
    val reduxdroid_core = "com.github.s0nerik.reduxdroid:core:${Versions.reduxdroid}"
    val reduxdroid_activity_result = "com.github.s0nerik.reduxdroid:activity_result:${Versions.reduxdroid}"
    val reduxdroid_state_serializer = "com.github.s0nerik.reduxdroid:state_serializer:${Versions.reduxdroid}"
    val reduxdroid_navigation = "com.github.s0nerik.reduxdroid:navigation:${Versions.reduxdroid}"
    val reduxdroid_livedata = "com.github.s0nerik.reduxdroid:livedata:${Versions.reduxdroid}"
    val reduxdroid_viewmodel = "com.github.s0nerik.reduxdroid:viewmodel:${Versions.reduxdroid}"

    val reduxdroid = arrayOf(
            reduxdroid_core,
            reduxdroid_activity_result,
            reduxdroid_state_serializer,
            reduxdroid_navigation,
            reduxdroid_livedata,
            reduxdroid_viewmodel
    )
    //endregion

    //region AndroidX
    val androidx_core = "androidx.core:core:${Versions.androidx}"
    val androidx_core_ktx = "androidx.core:core-ktx:${Versions.androidx}"

    val androidx_material = "com.google.android.material:material:${Versions.androidx}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
    val androidx_cardview = "androidx.cardview:cardview:${Versions.androidx}"
    val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx}"
    val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    val androidx_nav_runtime = "android.arch.navigation:navigation-runtime:${Versions.androidx_nav}"
    val androidx_nav_ui_ktx = "android.arch.navigation:navigation-ui-ktx:${Versions.androidx_nav}"
    val androidx_nav_fragment_ktx = "android.arch.navigation:navigation-fragment-ktx:${Versions.androidx_nav}"

    val androidx_lifecycle = arrayOf(
            "androidx.lifecycle:lifecycle-viewmodel:${Versions.androidx_lifecycle}",
            "androidx.lifecycle:lifecycle-livedata:${Versions.androidx_lifecycle}"
    )

    val androidx = arrayOf(
            androidx_core, androidx_core_ktx,
            androidx_appcompat, androidx_recyclerview, androidx_constraintlayout,
            androidx_material, androidx_cardview,
            androidx_nav_runtime, androidx_nav_ui_ktx, androidx_nav_fragment_ktx,
            *androidx_lifecycle
    )
    //endregion

    //region Various UI components
    val bindingCollectionAdapter = arrayOf(
            "com.github.s0nerik.binding-collection-adapter:bindingcollectionadapter:${Versions.bindingCollectionAdapter}",
            "com.github.s0nerik.binding-collection-adapter:bindingcollectionadapter-recyclerview:${Versions.bindingCollectionAdapter}",
            "com.github.s0nerik.binding-collection-adapter:bindingcollectionadapter-ktx:${Versions.bindingCollectionAdapter}"
    )
    //endregion

    //region Social
    val facebook_login = "com.facebook.android:facebook-login:${Versions.facebook}"
    //endregion

    //region Networking
    val okHttp = arrayOf(
        "com.squareup.okhttp3:okhttp:3.10.0",
        "com.github.simonpercic:oklog3:2.3.0",
        "com.squareup.okhttp3:logging-interceptor:3.10.0"
    )

    val retrofit = arrayOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    )

    val retrofit_adapter_coroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    //endregion

    //region Working with dates
    val kodatime = "com.github.debop:koda-time:1.2.2"
    //endregion

    //region Testing
    val junit = "junit:junit:4.12"
    val supportRunner = "com.android.support.test:runner:1.0.2"
    val supportEspresso = "com.android.support.test.espresso:espresso-core:3.0.2"
    val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1"
    val robolectric = "org.robolectric:robolectric:3.8"

    val unitTest = arrayOf(junit, mockito, robolectric)
    val androidTest = arrayOf(supportRunner, supportEspresso)
    //endregion

    //region Sets
    val module_basic = arrayOf(
            *kotlin, anko_commons, koin_core, reduxdroid_core
    )
    val module_ui = arrayOf(
            *kotlin, anko_commons, *koin, *reduxdroid, *androidx, *bindingCollectionAdapter
    )
    //endregion
}