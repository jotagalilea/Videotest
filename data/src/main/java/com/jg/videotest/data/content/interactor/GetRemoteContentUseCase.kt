package com.jg.videotest.data.content.interactor

import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.data.interactor.GetUseCase
import com.jg.videotest.model.ui.ContentUi

class GetRemoteContentUseCase(
    private val repository: ContentRepository
) : GetUseCase<List<ContentUi>>() {

    override suspend fun buildUseCaseObservable(): List<ContentUi> {
        return repository.getRemoteContents()
    }
}