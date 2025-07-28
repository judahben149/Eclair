package com.judahben149.eclair.data.llm

import com.judahben149.eclair.core.utils.logI
import com.judahben149.eclair.data.llm.impl.ApiLLMService
import com.judahben149.eclair.data.llm.impl.DummyLLMService
import com.judahben149.eclair.data.llm.impl.OnDeviceLLMService
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import kotlinx.coroutines.flow.first

class LLMServiceManager(
    private val onDeviceService: OnDeviceLLMService,
    private val apiService: ApiLLMService,
    private val dummyService: DummyLLMService,
    private val preferences: PreferencesDataStore
) {
    
    suspend fun getService(preferredType: LLMServiceType? = null): LLMService {
        val serviceType = preferredType ?: preferences.getPreferredLLMService().first()

        return when (serviceType) {
            LLMServiceType.ON_DEVICE -> {
                "Using OnDeviceService".logI()
                if (onDeviceService.isAvailable()) onDeviceService
                else getFallbackService()
            }
            LLMServiceType.API -> {
                "Using ApiService".logI()
                if (apiService.isAvailable()) apiService
                else getFallbackService()
            }
            LLMServiceType.DUMMY -> {
                "Using DummyService".logI()
                dummyService
            }
        }
    }

    suspend fun setPreferredService(serviceType: LLMServiceType) {
        preferences.setPreferredLLMService(serviceType)
    }
    
    private fun getFallbackService(): LLMService {
        return when {
            dummyService.isAvailable() -> dummyService
            onDeviceService.isAvailable() -> onDeviceService
            apiService.isAvailable() -> apiService
            else -> throw IllegalStateException("No LLM service available")
        }
    }
}