package com.judahben149.eclair.core.utils

import eclair.composeapp.generated.resources.Res
import java.nio.file.Files

//class DesktopResourceFileRetriever : ResourceFileRetriever {
//    
//    override suspend fun retrieveResourceFilePath(resPath: String): String {
//        val bytes = Res.readBytes(resPath)
//
//        val extension = getFileExtension(resPath)
//        val tmpFile = if (extension.isNotEmpty()) {
//            Files.createTempFile("eclair_resource", ".$extension").toFile()
//        } else {
//            Files.createTempFile("eclair_resource", ".tmp").toFile()
//        }
//
//        tmpFile.deleteOnExit()
//
//        tmpFile.writeBytes(bytes)
//
//        return tmpFile.absolutePath
//    }
//
//    private fun getFileExtension(filePath: String): String {
//        val lastDotIndex = filePath.lastIndexOf('.')
//        return if (lastDotIndex > 0 && lastDotIndex < filePath.length - 1) {
//            filePath.substring(lastDotIndex + 1)
//        } else {
//            ""
//        }
//    }
//}