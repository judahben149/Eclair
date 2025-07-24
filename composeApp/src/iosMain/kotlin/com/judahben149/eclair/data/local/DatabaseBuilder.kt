package com.judahben149.eclair.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory
//import com.judahben149.eclair.data.local.EclairDatabase.instantiateImpl

fun getEclairDatabaseBuilder(): EclairDatabase {
    val dbFile = "${NSHomeDirectory()}/eclair.db"

    return Room.databaseBuilder<EclairDatabase>(
        name = dbFile,
//        factory = { EclairDatabase::class.instantiateImpl()}
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}