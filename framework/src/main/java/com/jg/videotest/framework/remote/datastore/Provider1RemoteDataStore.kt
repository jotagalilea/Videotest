package com.jg.videotest.framework.remote.datastore

import android.util.Log
import com.jg.videotest.data.content.repository.datastore.ContentDataStore
import com.jg.videotest.framework.remote.mapper.Provider1RemoteMapper
import com.jg.videotest.framework.sources.remote.service.Provider1Service
import com.jg.videotest.framework.sources.remote.service.grpc.Provider1ServiceGRPC
import com.jg.videotest.model.Category
import com.jg.videotest.model.Video
import com.jg.videotest.model.ui.ContentUi
import java.net.UnknownHostException

class Provider1RemoteDataStore(
    //private val service: Provider1Service,
    private val service: Provider1ServiceGRPC,
    private val mapper: Provider1RemoteMapper
) : ContentDataStore {

    /*override suspend fun getContent(): List<ContentUi> {
        try {
            val response = service.getContent()
            Log.d(this.javaClass.simpleName, "Respuesta obtenida")
            if (response.isSuccessful) {
                response.body()?.let {
                    val mappedContent =  mapper.mapToModel(it)
                    return generateList(mappedContent.videos, mappedContent.categories)
                }
            }
        }
        catch (e: UnknownHostException) {
            Log.e(this.javaClass.simpleName, "No ha sido posible conectar.")
            e.printStackTrace()
        }
        return listOf()
    }*/

    override suspend fun getContent(): List<ContentUi> {
        try {
            val response = service.getContent()
            Log.d(this.javaClass.simpleName, "Respuesta obtenida")
            val mappedContent = mapper.mapToModel(response)
            return generateList(mappedContent.videos, mappedContent.categories)
        }
        catch (e: UnknownHostException) {
            Log.e(this.javaClass.simpleName, "No ha sido posible conectar.")
            e.printStackTrace()
        }
        return listOf()
    }

    private fun generateList(videos: List<Video>, categories: List<Category>): List<ContentUi> {
        val contentMap = HashMap<Int, ContentUi>()

        videos.forEach { video ->
            var item: ContentUi? = contentMap[video.categoryId]
            if (item == null) {
                item = ContentUi(
                    categories.first { video.categoryId == it.id },
                    mutableListOf(),
                    true
                )
                contentMap[video.categoryId] = item
            }
            item.videosList.add(video)
        }
        return contentMap.values.map { it }
    }

}