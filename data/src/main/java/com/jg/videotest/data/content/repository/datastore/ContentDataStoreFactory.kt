package com.jg.videotest.data.content.repository.datastore

class ContentDataStoreFactory(
    private val cachedDataStore: ContentCacheDataStore,
    private val remote1DataStore: ContentDataStore
) {

    fun getCachedDataStore(): ContentCacheDataStore = cachedDataStore
    fun getRemote1DataStore(): ContentDataStore = remote1DataStore

}