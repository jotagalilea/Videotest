package com.jg.videotest.framework.local.dao

import androidx.room.*
import com.jg.videotest.framework.FrameworkUtils.CATEGORIES_TABLE_NAME
import com.jg.videotest.framework.local.model.content.CategoryDBObject

@Dao
abstract class CategoriesDao {

    @Transaction
    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    abstract suspend fun queryCategories(): List<CategoryDBObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategory(item: CategoryDBObject)

}