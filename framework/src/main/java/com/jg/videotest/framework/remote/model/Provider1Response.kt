package com.jg.videotest.framework.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Provider1Response(
    val categories: List<CategoryResponse>,
    val videos: List<VideoResponse>
) {
    inner class CategoryResponse(
        val id: Int?,
        val title: String?,
        val type: String?
    )

    inner class VideoResponse(
        val id: String?,
        val thumb: String?,
        val videoUrl: String?,
        val categoryId: Int?,
        val name: String?
    )
}