package com.jg.videotest.framework.local.mapper

import com.jg.videotest.framework.local.model.content.CategoryDBObject
import com.jg.videotest.framework.sources.local.mapper.CacheMapper
import com.jg.videotest.model.Category

class CategoryCacheMapper: CacheMapper<CategoryDBObject, Category>{

    override fun mapToModel(item: CategoryDBObject) = Category(
        id = item.id,
        title = item.title,
        type = item.type
    )

    override fun mapToCached(item: Category): CategoryDBObject = CategoryDBObject(
        id = item.id,
        title = item.title,
        type = item.type
    )

}