package com.jg.videotest.framework.remote.mapper

import com.jg.videotest.framework.remote.model.Provider1Model
import com.jg.videotest.framework.remote.model.Provider1Response
import com.jg.videotest.framework.sources.remote.mapper.RemoteMapper
import com.jg.videotest.model.Category
import com.jg.videotest.model.Video

class Provider1RemoteMapper: RemoteMapper<Provider1Response, Provider1Model> {

    override fun mapToModel(item: Provider1Response): Provider1Model {
        return Provider1Model(
            categories = mapCategoryResponse(item.categories),
            videos = mapVideoResponse(item.videos)
        )
    }

    private fun mapCategoryResponse(items: List<Provider1Response.CategoryResponse>): List<Category> {
        return items.map { item ->
            Category(
                id = item.id ?: -1,
                title = item.title ?: "",
                type = item.type ?: ""
            )
        }
    }

    private fun mapVideoResponse(items: List<Provider1Response.VideoResponse>): List<Video> {
        return items.map { item ->
            Video(
                id = item.id ?: "NOT_FOUND",
                thumb = item.thumb ?: "",
                videoUrl = item.videoUrl ?: "",
                categoryId = item.categoryId ?: -1,
                name = item.name ?: ""
            )
        }
    }
}