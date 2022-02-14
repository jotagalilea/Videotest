package com.jg.videotest.data.content.interactor

import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.data.interactor.SaveUseCase
import com.jg.videotest.model.ui.ContentUi

class SaveContentUseCase(
    private val repository: ContentRepository
) : SaveUseCase<ContentUi>() {

    override suspend fun buildUseCaseObservable(item: ContentUi) {
        repository.saveContent(item)
    }
}