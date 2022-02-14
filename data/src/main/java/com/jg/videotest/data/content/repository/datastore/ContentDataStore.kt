package com.jg.videotest.data.content.repository.datastore

import com.jg.videotest.model.ui.ContentUi

interface ContentDataStore {

    suspend fun getContent(): List<ContentUi>

}