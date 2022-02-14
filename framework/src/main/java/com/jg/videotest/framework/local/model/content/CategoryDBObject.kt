package com.jg.videotest.framework.local.model.content

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jg.videotest.framework.FrameworkUtils

@Entity(tableName = FrameworkUtils.CATEGORIES_TABLE_NAME)
data class CategoryDBObject(
    @PrimaryKey
    val id: Int,
    val title: String,
    val type: String
)