package com.jg.videotest.framework.sources.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jg.videotest.framework.local.dao.CategoriesDao
import com.jg.videotest.framework.local.dao.VideosDao
import com.jg.videotest.framework.local.model.content.CategoryDBObject
import com.jg.videotest.framework.local.model.content.VideoDBObject

@Database(
    entities = [
        VideoDBObject::class, CategoryDBObject::class
    ],
    version = 1
)
abstract class ContentDatabase: RoomDatabase() {

    abstract fun videosDao(): VideosDao
    abstract fun categoriesDao(): CategoriesDao

}