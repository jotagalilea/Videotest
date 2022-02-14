package com.jg.videotest.data.interactor

abstract class GetUseCase<T> {

    protected abstract suspend fun buildUseCaseObservable(): T

    suspend fun execute(): T {
        return buildUseCaseObservable()
    }
}