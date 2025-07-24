package com.judahben149.eclair.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getEclairDatabaseBuilder(context: Context): EclairDatabase {
    val dbFile = context.getDatabasePath("eclair.db")

    return Room.databaseBuilder<EclairDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}