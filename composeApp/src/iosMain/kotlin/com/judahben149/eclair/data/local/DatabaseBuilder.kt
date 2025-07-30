package com.judahben149.eclair.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getEclairDatabaseBuilder(): EclairDatabase {
    val dbFile = "${NSHomeDirectory()}/eclair.db"

    return Room.databaseBuilder<EclairDatabase>(
        name = dbFile,
    )
        .setDriver(BundledSQLiteDriver())
//        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}