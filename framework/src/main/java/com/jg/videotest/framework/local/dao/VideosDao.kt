package com.jg.videotest.framework.local.dao

import androidx.room.*
import com.jg.videotest.framework.FrameworkUtils.VIDEOS_TABLE_NAME
import com.jg.videotest.framework.local.model.content.VideoDBObject

@Dao
abstract class VideosDao {

    @Transaction
    @Query("SELECT * FROM $VIDEOS_TABLE_NAME")
    abstract suspend fun queryVideos(): List<VideoDBObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertVideo(item: VideoDBObject)

}