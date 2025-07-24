package com.judahben149.eclair.data.local

import androidx.room.Room
import java.io.File

fun getEclairDatabaseBuilder(): EclairDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "eclair.db")

    return Room.databaseBuilder<EclairDatabase>(
        name = dbFile.absolutePath,
    ).build()
}