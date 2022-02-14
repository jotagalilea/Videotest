package com.jg.videotest.framework.local.model.content

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jg.videotest.framework.FrameworkUtils

@Entity(
    tableName = FrameworkUtils.VIDEOS_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CategoryDBObject::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("categoryId")
    ]
)
data class VideoDBObject(
    @PrimaryKey
    val id: String,
    val thumb: String,
    val videoUrl: String,
    val categoryId: Int,
    val name: String
)
