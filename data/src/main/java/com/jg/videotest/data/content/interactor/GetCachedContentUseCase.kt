package com.jg.videotest.data.content.interactor

import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.data.interactor.GetUseCase
import com.jg.videotest.model.ui.ContentUi

class GetCachedContentUseCase(
    private val repository: ContentRepository
) : GetUseCase<List<ContentUi>>() {

    override suspend fun buildUseCaseObservable(): List<ContentUi> {
        return repository.getCachedContents()
    }
}