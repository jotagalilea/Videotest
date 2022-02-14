package com.jg.videotest.framework.sources.local.mapper

interface CacheMapper<Cache, Model> {

    fun mapToModel(item: Cache): Model
    fun mapToCached(item: Model): Cache

}