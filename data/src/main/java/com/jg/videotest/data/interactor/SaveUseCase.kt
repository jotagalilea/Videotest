package com.jg.videotest.data.interactor

abstract class SaveUseCase<T> {

    protected abstract suspend fun buildUseCaseObservable(item: T)

    suspend fun execute(item: T) {
        return buildUseCaseObservable(item)
    }
}