package com.judahben149.eclair.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        "${System.getProperty("user.home")}/.Eclair/".plus(DATA_STORE_FILE_NAME)
    }
}