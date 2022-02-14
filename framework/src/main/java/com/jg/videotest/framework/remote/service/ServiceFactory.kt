package com.jg.videotest.framework.remote.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    fun makeProvider1Service(): Provider1Service {
        return Retrofit.Builder()
            .baseUrl(Provider1Service.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Provider1Service::class.java)
    }

}