package com.judahben149.eclair.core.utils

import dev.kursor.ktensorflow.api.ModelDesc

interface ResourceFileRetriever {
    suspend fun retrieveResourceFilePath(resPath: String): String

    suspend fun retrieveModelDesc(resPath: String): ModelDesc
}