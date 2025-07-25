package com.judahben149.eclair.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val PREFERRED_LLM_SERVICE = stringPreferencesKey("preferred_llm_service")
    val API_BASE_URL = stringPreferencesKey("api_base_url")
    val API_KEY = stringPreferencesKey("api_key")
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val STREAMING_ENABLED = booleanPreferencesKey("streaming_enabled")
}