package com.jg.videotest.framework.sources.remote.service

import com.jg.videotest.framework.remote.model.Provider1Response
import retrofit2.Response
import retrofit2.http.GET

interface Provider1Service {

    companion object {
        const val BASE_URL = "https://drive.google.com/"
        const val CONTENT_URL = "uc?id=1ldaCVV28Xtk-EVXhv7xh8ebR9kzfha0v"
    }

    @GET(CONTENT_URL)
    suspend fun getContent(): Response<Provider1Response>
}