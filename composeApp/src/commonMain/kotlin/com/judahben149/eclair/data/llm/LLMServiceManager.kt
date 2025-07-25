package com.judahben149.eclair.data.llm

import com.judahben149.eclair.data.llm.impl.ApiLLMService
import com.judahben149.eclair.data.llm.impl.DummyLLMService
import com.judahben149.eclair.data.llm.impl.OnDeviceLLMService

class LLMServiceManager(
    private val onDeviceService: OnDeviceLLMService,
    private val apiService: ApiLLMService,
    private val dummyService: DummyLLMService,
    private val preferences: UserPreferences
) {
    
    fun getService(preferredType: LLMServiceType? = null): LLMService {
        val serviceType = preferredType ?: preferences.getPreferredLLMService()
        
        return when (serviceType) {
            LLMServiceType.ON_DEVICE -> {
                if (onDeviceService.isAvailable()) onDeviceService
                else getFallbackService()
            }
            LLMServiceType.API -> {
                if (apiService.isAvailable()) apiService
                else getFallbackService()
            }
            LLMServiceType.DUMMY -> dummyService
        }
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