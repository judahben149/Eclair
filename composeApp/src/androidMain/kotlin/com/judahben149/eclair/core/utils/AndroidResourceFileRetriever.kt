package com.judahben149.eclair.core.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dev.kursor.ktensorflow.api.ModelDesc
import eclair.composeapp.generated.resources.Res
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption

class AndroidResourceFileRetriever(
    private val context: Context
) : ResourceFileRetriever {

    override suspend fun retrieveResourceFilePath(resPath: String): String {
        return createTempFileFromResource(resPath).path
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun retrieveModelDesc(resPath: String): ModelDesc {
        val resourceUri = Res.getUri(resPath)
        "Resource URI: $resourceUri".logI()

        val file = when {
            resourceUri.contains("android_asset") -> {
                "Creating from Asset".logI()
                createTempFileFromAsset(resourceUri)
            }
            resourceUri.startsWith("file://") && !resourceUri.contains("android_asset") -> {
                "Using real file path".logI()
                val filePath = resourceUri.removePrefix("file://")
                val realFile = File(filePath)
                if (realFile.exists()) {
                    realFile
                } else {
                    "Real file doesn't exist, creating temp file".logI()
                    createTempFileFromResource(resPath)
                }
            }
            File(resourceUri).exists() -> {
                "Using resource uri as direct path".logI()
                File(resourceUri)
            }
            else -> {
                "Fallback: Creating temp file from resource".logI()
                createTempFileFromResource(resPath)
            }
        }

        "Final file path: ${file.absolutePath}, exists: ${file.exists()}, size: ${file.length()}".logI()

        val fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ)
        val byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
            .apply { order(ByteOrder.nativeOrder()) }

        return ModelDesc.ByteBuffer(byteBuffer)
    }

    private suspend fun createTempFileFromAsset(assetUri: String): File {
        // Handle both android_asset:// and file:///android_asset/ formats
        val assetPath = assetUri
            .removePrefix("android_asset://")
            .removePrefix("file:///android_asset/")
            .removePrefix("file://android_asset/")
            .removePrefix("/android_asset/")

        "Asset path after cleanup: $assetPath".logI()

        val fileName = assetPath.substringAfterLast('/')
        val cachedFile = File(context.cacheDir, fileName)

        if (!cachedFile.exists() || cachedFile.length() == 0L) {
            "Extracting asset to cache: $assetPath".logI()
            try {
                context.assets.open(assetPath).use { inputStream ->
                    cachedFile.sink().buffer().use { bufferedSink ->
                        inputStream.source().buffer().use { bufferedSource ->
                            bufferedSink.writeAll(bufferedSource)
                        }
                    }
                }
                "Asset extracted successfully. File size: ${cachedFile.length()}".logI()
            } catch (e: Exception) {
                "Failed to extract asset: ${e.message}".logE()
                throw e
            }
        } else {
            "Using existing cached file: ${cachedFile.absolutePath}".logI()
        }

        return cachedFile
    }

    private suspend fun createTempFileFromResource(resPath: String): File {
        val fileName = resPath.substringAfterLast('/')
        val cachedFile = File(context.cacheDir, fileName)

        if (!cachedFile.exists() || cachedFile.length() == 0L) {
            val resourceUri = Res.getUri(resPath)
            "Creating temp file from resource URI: $resourceUri".logI()

            when {
                resourceUri.contains("android_asset") -> {
                    val assetPath = resourceUri
                        .removePrefix("android_asset://")
                        .removePrefix("file:///android_asset/")
                        .removePrefix("file://android_asset/")
                        .removePrefix("/android_asset/")

                    context.assets.open(assetPath).use { inputStream ->
                        cachedFile.sink().buffer().use { bufferedSink ->
                            inputStream.source().buffer().use { bufferedSource ->
                                bufferedSink.writeAll(bufferedSource)
                            }
                        }
                    }
                }
                else -> {
                    throw IllegalStateException("Unsupported resource URI: $resourceUri")
                }
            }
        }

        return cachedFile
    }
}

