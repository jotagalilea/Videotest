package com.jg.videotest.ui.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.jg.videotest.R
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
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: KoinTest {

    /**
     * -> Reglas:
     *      - Timeout para DB y red.
     *
     * -> Before:
     *      - Preparar los useCase.
     *      - Preparar contentList y contentStateFlow.
     *
     * -> Durante:
     *      - Mockear los resultados de tod0 tipo.
     *
     * -> After:
     *      - ¿Nada?
     */

    //TODO: ¿Se puede inicializar todo esto con Mockito?
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


    //TODO: Buscar en más sitios la utilidad de las Rules.
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(listOf(applicationModule, contentModule))
    }

    @get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
    }
    
    @Before
    fun setUp() {
        contentList = mutableListOf()
        contentStateFlow = MutableStateFlow(DataStatus.Empty(""))
        repository = mock()
        //TODO: ¿Debería mockear los UseCase?
        getCachedContent = GetCachedContentUseCase(repository)
        getRemoteContent = GetRemoteContentUseCase(repository)
        saveContent = SaveContentUseCase(repository)
    }

    @After
    fun tearDown() {}

    // FIXME: JUnit no está recibiendo los events...
    @Test
    @ExperimentalCoroutinesApi
    private fun `WHEN fetchContent() from DB, IF "in time" AND not empty THEN Success`() = runTest {
        contentStateFlow.value = DataStatus.Loading()
        //TODO: Creo que debo hacerme sí o sí una contentList "real":
        Mockito.`when`(getCachedContent.execute()).doReturn(TestFactory.createContentList())
        assertTrue(contentStateFlow.value is DataStatus.Success)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun fetchMockContent(context: Context) = CoroutineScope(Dispatchers.Default).launch {
        contentStateFlow.value = DataStatus.Loading()

        getCachedContent.execute().also { cachedList ->
            if (cachedList.isEmpty()) {
                contentStateFlow.value = DataStatus.Loading()
                contentList.clear()
                getRemoteContent.execute().also { responseList ->
                    if (responseList.isEmpty())
                        contentStateFlow.value = DataStatus.Empty(context.getString(R.string.no_content))
                    else {
                        responseList.onEach { content ->
                            contentList.add(content)
                            saveContent.execute(content)
                        }
                        contentStateFlow.value = DataStatus.Success(contentList)
                    }
                }
            }
            else {
                if (contentList.isEmpty())
                    contentList.addAll(cachedList)
                contentStateFlow.value = DataStatus.Success(contentList)
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
}