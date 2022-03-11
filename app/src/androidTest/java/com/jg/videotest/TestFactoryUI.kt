package com.jg.videotest

import com.jg.videotest.model.Category
import com.jg.videotest.model.Video
import com.jg.videotest.model.ui.ContentUi

object TestFactoryUI {

    fun createContentList(): List<ContentUi>{
        val list = mutableListOf<ContentUi>()
        for (cats in 1..3) {
            list.add(
                ContentUi(
                    Category(
                        id = cats,
                        title = "CategoryId_$cats",
                        type = "Type$cats"
                    ),
                    mutableListOf(
                        Video(
                            id = "VideoId_1",
                            name = "Name1",
                            categoryId = cats,
                            thumb = "Thumb1",
                            videoUrl = "Url1"
                        ),
                        Video(
                            id = "VideoId_2",
                            name = "Name2",
                            categoryId = cats,
                            thumb = "Thumb2",
                            videoUrl = "Url2"
                        )
                    ),
                    folded = true
                )
            )
        }
        return list
    }

}