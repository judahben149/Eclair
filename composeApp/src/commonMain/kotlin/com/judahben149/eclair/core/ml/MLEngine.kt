package com.judahben149.eclair.core.ml

import kotlinx.coroutines.CoroutineScope

interface MLEngine{

    suspend fun loadModel(coroutineScope: CoroutineScope)
    suspend fun predict(coroutineScope: CoroutineScope, input: FloatArray): FloatArray
}