package com.judahben149.eclair.core.utils

import android.content.Context
import dev.kursor.ktensorflow.api.ModelDesc
import eclair.composeapp.generated.resources.Res
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AndroidResourceFileRetriever(
    private val context: Context
) : ResourceFileRetriever {

    override suspend fun retrieveResourceFilePath(resPath: String): String {
        val bytes = Res.readBytes(resPath)
        val tmpFile = File.createTempFile("prefix", "suffix", context.cacheDir)
        tmpFile.writeBytes(bytes)
        return tmpFile.path
    }

    override suspend fun retrieveModelDesc(resPath: String): ModelDesc {
        val bytes = Res.readBytes("files/gpt2_8bits.tflite")
        val byteBuffer = ByteBuffer.wrap(bytes).apply { order(ByteOrder.nativeOrder()) }
        val modelDesc = ModelDesc.ByteBuffer(byteBuffer)

        return modelDesc
    }
}