package com.jg.videotest.framework.sources.remote.service.grpc

import com.google.gson.Gson
import com.jg.videotest.framework.remote.model.Provider1Response
import io.grpc.*
import io.grpc.MethodDescriptor.Marshaller
import io.grpc.MethodDescriptor.MethodType
import io.grpc.android.AndroidChannelBuilder
import io.grpc.okhttp.OkHttpChannelBuilder
import io.grpc.stub.ClientCalls
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI

class Provider1ServiceGRPC {

    companion object {
        //const val BASE_URL = "https://drive.google.com/"
        const val BASE_URL = "drive.google.com/"
        const val CONTENT_URL = "uc?id=1ldaCVV28Xtk-EVXhv7xh8ebR9kzfha0v"
        const val CONTENT_TARGET = BASE_URL + CONTENT_URL
        const val CONTENT_PORT = 443
        const val GET_CONTENT_FULL_NAME = "getContentDataFromRemoteService"
        const val GET_CONTENT_SHORT_NAME = "getContent"
    }

    private val channel = AndroidChannelBuilder.forAddress(CONTENT_TARGET, 0)
        .usePlaintext()
        .build()


    private val methodDescriptor: MethodDescriptor<Unit, Provider1Response> =
        MethodDescriptor.newBuilder(
            createMarshallerFor(Unit.javaClass),
            createMarshallerFor(Provider1Response::class.java)
        )
            .setFullMethodName(MethodDescriptor.generateFullMethodName(GET_CONTENT_FULL_NAME, GET_CONTENT_SHORT_NAME))
            .setType(MethodType.UNARY)
            .build()


    private fun <T> createMarshallerFor(clazz: Class<T>): Marshaller<T> {
        val gson = Gson()
        val marshaller = (object: Marshaller<T> {
            override fun stream(value: T): InputStream {
                return ByteArrayInputStream(gson.toJson(value, clazz).toByteArray())
            }

            override fun parse(stream: InputStream?): T {
                return gson.fromJson(InputStreamReader(stream, Charsets.UTF_8), clazz)
            }

        })
        return marshaller
    }

    suspend fun getContent(): Provider1Response {
        val call: ClientCall<Unit, Provider1Response> = channel.newCall(methodDescriptor, CallOptions.DEFAULT)
        return ClientCalls.blockingUnaryCall(call, null)
    }
}