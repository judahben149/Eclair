package com.judahben149.eclair.core.utils

import dev.kursor.ktensorflow.api.ModelDesc
import eclair.composeapp.generated.resources.Res
import platform.Foundation.NSBundle

class IosResourceFileRetriever : ResourceFileRetriever {
    override suspend fun retrieveResourceFilePath(resPath: String): String {
        return NSBundle.mainBundle
            .pathForResource(
                name = "gpt2_8bits",
                ofType = "tflite",
                inDirectory = "compose-resources/composeResources/eclair.composeapp.generated.resources/files"
            )!!
    }

    override suspend fun retrieveModelDesc(resPath: String): ModelDesc {
        return ModelDesc.PathInBundle(Res.getUri("files/gpt2_8bits.tflite").removePrefix("file://"))
    }
}