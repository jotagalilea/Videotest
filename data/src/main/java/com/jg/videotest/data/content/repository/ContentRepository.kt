package com.jg.videotest.data.content.repository

import com.jg.videotest.model.ui.ContentUi

interface ContentRepository {

    suspend fun getCachedContents(): List<ContentUi>
    suspend fun getRemoteContents(): List<ContentUi>
    suspend fun saveContent(item: ContentUi)

}