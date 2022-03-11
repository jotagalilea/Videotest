package com.jg.videotest.ui.main

import com.jg.videotest.data.content.interactor.GetCachedContentUseCase
import com.jg.videotest.data.content.interactor.GetRemoteContentUseCase
import com.jg.videotest.data.content.interactor.SaveContentUseCase
import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.di.applicationModule
import com.jg.videotest.di.contentModule
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.common.DataStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: KoinTest {

    @Mock
    private lateinit var repository: ContentRepository
    @Mock
    private lateinit var getCachedContent: GetCachedContentUseCase
    @Mock
    private lateinit var getRemoteContent: GetRemoteContentUseCase
    @Mock
    private lateinit var saveContent: SaveContentUseCase
    private lateinit var contentList: MutableList<ContentUi>
    private lateinit var contentStateFlow: MutableStateFlow<DataStatus<List<ContentUi>>>


    @get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
    }

    @Before
    fun setUp() {
        startKoin {
            androidLogger(Level.ERROR)
            modules(listOf(applicationModule, contentModule))
        }
        contentList = mutableListOf()
        contentStateFlow = MutableStateFlow(DataStatus.Empty(""))
        repository = mock()
        getCachedContent = GetCachedContentUseCase(repository)
        getRemoteContent = GetRemoteContentUseCase(repository)
        saveContent = SaveContentUseCase(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchCachedContentAndSuccess() = runTest {
        contentStateFlow.value = DataStatus.Loading()
        Mockito.`when`(getCachedContent.execute()).doReturn(TestFactory.createContentList())
        contentStateFlow.value = DataStatus.Success(getCachedContent.execute())
        assertTrue(contentStateFlow.value is DataStatus.Success)
    }

}