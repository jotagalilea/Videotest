package com.jg.videotest.model.ui

import com.jg.videotest.model.Category
import com.jg.videotest.model.Video

data class ContentUi(
    val category: Category,
    val videosList: MutableList<Video>,
    var folded: Boolean
)