package com.jg.videotest.framework.local.model.datastore

import com.jg.videotest.data.content.repository.datastore.ContentCacheDataStore
import com.jg.videotest.framework.local.mapper.CategoryCacheMapper
import com.jg.videotest.framework.local.mapper.VideoCacheMapper
import com.jg.videotest.framework.sources.local.db.ContentDatabase
import com.jg.videotest.model.Category
import com.jg.videotest.model.Video
import com.jg.videotest.model.ui.ContentUi

class ContentCachedDataStore(
    private val database: ContentDatabase,
    private val videosMapper: VideoCacheMapper,
    private val categoriesMapper: CategoryCacheMapper
) : ContentCacheDataStore {

    override suspend fun getContent(): List<ContentUi> {
        val videos = database.videosDao().queryVideos().map { videoDB ->
            videosMapper.mapToModel(videoDB)
        }
        val categories = database.categoriesDao().queryCategories().map { categoryDB ->
            categoriesMapper.mapToModel(categoryDB)
        }
        return generateList(videos, categories)
    }

    private fun generateList(videos: List<Video>, categories: List<Category>): List<ContentUi> {
        val contentMap = HashMap<Int, ContentUi>()

        videos.forEach { video ->
            var item: ContentUi? = contentMap[video.categoryId]
            if (item == null) {
                item = ContentUi(
                    categories.first { video.categoryId == it.id },
                    mutableListOf(),
                    true
                )
                contentMap[video.categoryId] = item
            }
            item.videosList.add(video)
        }
        return contentMap.values.map { it }
    }

    override suspend fun saveContent(item: ContentUi) {
        database.categoriesDao().insertCategory(categoriesMapper.mapToCached(item.category))
        item.videosList.forEach {
            database.videosDao().insertVideo(videosMapper.mapToCached(it))
        }
    }
}