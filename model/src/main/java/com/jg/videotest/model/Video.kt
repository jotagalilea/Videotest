package com.jg.videotest.model

import java.io.Serializable

data class Video(
    val id: String,
    val thumb: String,
    val videoUrl: String,
    val categoryId: Int,
    val name: String
): Serializable
