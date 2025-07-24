package com.judahben149.eclair.di

import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import org.koin.dsl.module

fun platformModule() = module {
    single<EclairDatabase> { getEclairDatabaseBuilder() }
}