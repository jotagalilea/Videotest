package com.jg.videotest.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.videotest.R
import com.jg.videotest.data.content.interactor.GetCachedContentUseCase
import com.jg.videotest.data.content.interactor.GetRemoteContentUseCase
import com.jg.videotest.data.content.interactor.SaveContentUseCase
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.common.DataStatus
import com.jg.videotest.ui.common.DataStatus.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCachedContent: GetCachedContentUseCase,
    private val getRemoteContent: GetRemoteContentUseCase,
    private val saveContent: SaveContentUseCase
) : ViewModel() {

    private val contentList: MutableList<ContentUi> = mutableListOf()
    private val contentStateFlow = MutableStateFlow<DataStatus<List<ContentUi>>>(Empty(""))
    var lastUnfoldedSavedState: Int = -1


    fun getContentStateFlow() = contentStateFlow.asStateFlow()

    fun fetchContent(context: Context) = viewModelScope.launch {
        contentStateFlow.value = Loading()

        getCachedContent.execute().also { cachedList ->
            if (cachedList.isEmpty()) {
                contentStateFlow.value = Loading()
                contentList.clear()
                getRemoteContent.execute().also { responseList ->
                    if (responseList.isEmpty())
                        contentStateFlow.value = Empty(context.getString(R.string.no_content))
                    else {
                        responseList.onEach { content ->
                            contentList.add(content)
                            saveContent.execute(content)
                        }
                        contentStateFlow.value = Success(contentList)
                    }
                }
            }
            else {
                if (contentList.isEmpty())
                    contentList.addAll(cachedList)
                contentStateFlow.value = Success(contentList)
            }
        }
    }

}