package com.judahben149.eclair.core.ml

import com.judahben149.eclair.core.utils.ResourceFileRetriever
import dev.kursor.ktensorflow.api.Interpreter
import dev.kursor.ktensorflow.api.InterpreterOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MLEngineImpl(
    private val resourceFileRetriever: ResourceFileRetriever
) : MLEngine {

    private var interpreter: Interpreter? = null

    override suspend fun loadModel(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            if (interpreter == null) {
                interpreter = Interpreter(
                    modelDesc = resourceFileRetriever
                        .retrieveModelDesc("files/gpt2_8bits.tflite"),
                    options = InterpreterOptions()
                )
            }
        }
    }

    override suspend fun predict(
        coroutineScope: CoroutineScope,
        input: FloatArray
    ): FloatArray {
        TODO("Not yet implemented")
    }


}