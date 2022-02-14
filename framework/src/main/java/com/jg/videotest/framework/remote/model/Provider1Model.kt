package com.jg.videotest.framework.remote.model

import com.jg.videotest.model.Category
import com.jg.videotest.model.Video

data class Provider1Model(
    val categories: List<Category>,
    val videos: List<Video>
) {

}