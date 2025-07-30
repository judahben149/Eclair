package com.judahben149.eclair.core.ml

import kotlinx.coroutines.CoroutineScope

class MockMLEngine : MLEngine {
    override suspend fun loadModel(coroutineScope: CoroutineScope) {
        TODO("Not yet implemented")
    }

    override suspend fun predict(
        coroutineScope: CoroutineScope,
        input: FloatArray
    ): FloatArray {
        TODO("Not yet implemented")
    }

}