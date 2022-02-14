package com.jg.videotest.data.content.repository

import com.jg.videotest.data.content.repository.datastore.ContentDataStoreFactory
import com.jg.videotest.model.ui.ContentUi

class ContentDataRepository(
    private val factory: ContentDataStoreFactory
) : ContentRepository {

    override suspend fun getCachedContents(): List<ContentUi> {
        return factory.getCachedDataStore().getContent()
    }

    override suspend fun getRemoteContents(): List<ContentUi> {
        return factory.getRemote1DataStore().getContent()
    }

    override suspend fun saveContent(item: ContentUi) {
        return factory.getCachedDataStore().saveContent(item)
    }
}