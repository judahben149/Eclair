package com.judahben149.eclair.di

import android.content.Context
import com.judahben149.eclair.core.Platform
import com.judahben149.eclair.core.ml.EquipmentClassifier
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import com.judahben149.eclair.data.preferences.createDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<EclairDatabase> { getEclairDatabaseBuilder(context) }
    single { get<EclairDatabase>().chatDao() }
    single { get<EclairDatabase>().conceptDao() }
    single<Platform> { Platform(androidApplication()) }
    single { EquipmentClassifier(androidContext()) }
}

val dataStoreModule = module {
    single { createDataStore(androidContext()) }
    single { PreferencesDataStore(get()) }
}