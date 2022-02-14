package com.jg.videotest.framework.sources.remote.mapper

interface RemoteMapper<Remote, Model> {

    fun mapToModel(item: Remote): Model

}