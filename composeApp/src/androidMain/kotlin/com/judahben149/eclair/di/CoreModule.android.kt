package com.judahben149.eclair.di

import android.content.Context
import com.judahben149.eclair.core.Platform
//import com.judahben149.eclair.core.ml.createModelDesc
import com.judahben149.eclair.core.utils.AndroidResourceFileRetriever
import com.judahben149.eclair.core.utils.ResourceFileRetriever
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import com.judahben149.eclair.data.preferences.createDataStore
import org.koin.android.ext.koin.androidApplication
//import dev.kursor.ktensorflow.api.ModelDesc
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<EclairDatabase> { getEclairDatabaseBuilder(context) }
    single<Platform> { Platform(androidApplication()) }
    single<ResourceFileRetriever> { AndroidResourceFileRetriever(androidContext()) }
}

val dataStoreModule = module {
    single { createDataStore(androidContext()) }
    single { PreferencesDataStore(get()) }
}

//fun tensorFlowModule() = module {
////    single<ModelDesc> { createModelDesc(get()) }
//}