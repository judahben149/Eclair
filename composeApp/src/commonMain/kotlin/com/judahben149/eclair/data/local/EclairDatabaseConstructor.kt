package com.judahben149.eclair.data.local

import androidx.room.RoomDatabaseConstructor

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object EclairDatabaseConstructor: RoomDatabaseConstructor<EclairDatabase> {
    override fun initialize(): EclairDatabase
}