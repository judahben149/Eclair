package com.judahben149.eclair.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.judahben149.eclair.data.llm.LLMServiceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStore(
    private val dataStore: DataStore<Preferences>
) {
    
    fun getPreferredLLMService(): Flow<LLMServiceType> =
        dataStore.data.map { preferences ->
            val serviceType = preferences[PreferencesKeys.PREFERRED_LLM_SERVICE]
            LLMServiceType.valueOf(serviceType ?: LLMServiceType.API.name)
        }
    
    suspend fun setPreferredLLMService(service: LLMServiceType) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_LLM_SERVICE] = service.name
        }
    }
    
    fun getApiKey(): Flow<String?> = 
        dataStore.data.map { it[PreferencesKeys.API_KEY] }
    
    suspend fun setApiKey(key: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.API_KEY] = key
        }
    }
    
    fun isStreamingEnabled(): Flow<Boolean> = 
        dataStore.data.map { it[PreferencesKeys.STREAMING_ENABLED] ?: true }
    
    suspend fun setStreamingEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STREAMING_ENABLED] = enabled
        }
    }
}