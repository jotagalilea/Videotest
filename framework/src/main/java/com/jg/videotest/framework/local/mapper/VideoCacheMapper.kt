package com.jg.videotest.framework.local.mapper

import com.jg.videotest.framework.local.model.content.VideoDBObject
import com.jg.videotest.model.Video

class VideoCacheMapper {

    fun mapToModel(item: VideoDBObject) = Video(
        id = item.id,
        thumb = item.thumb,
        videoUrl = item.videoUrl,
        categoryId = item.categoryId,
        name = item.name
    )

    fun mapToCached(item: Video) = VideoDBObject(
        id = item.id,
        thumb = item.thumb,
        videoUrl = item.videoUrl,
        categoryId = item.categoryId,
        name = item.name
    )
}