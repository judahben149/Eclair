package com.judahben149.eclair.di

import android.content.Context
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<EclairDatabase> { getEclairDatabaseBuilder(context) }
}