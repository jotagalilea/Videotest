package com.jg.videotest.data.content.repository.datastore

import com.jg.videotest.model.ui.ContentUi

interface ContentCacheDataStore: ContentDataStore {

    suspend fun saveContent(item: ContentUi)

}