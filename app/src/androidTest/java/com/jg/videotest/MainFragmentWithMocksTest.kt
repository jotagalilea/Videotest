package com.jg.videotest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jg.videotest.data.content.interactor.GetCachedContentUseCase
import com.jg.videotest.data.content.interactor.GetRemoteContentUseCase
import com.jg.videotest.data.content.interactor.SaveContentUseCase
import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.adapter.CategoriesAdapter
import com.jg.videotest.ui.common.DataStatus
import com.jg.videotest.ui.main.MainActivity
import com.jg.videotest.ui.main.MainFragment
import com.jg.videotest.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentWithMocksTest {

    /**
     * Casos a testear:
     * 0. ¿Faltarían casos para cuando no se pueden cargar los ítems?
     * 1. Desplegar un ítem de la lista.
     * 2. Plegar un ítem de la lista.
     * 3. Desplegar un ítem habiendo otro desplegado (y plegar el otro).
     * 4. Desplegar ítem, hacer scroll hasta el final, pulsar vídeo y transicionar al reproductor.
     *
     * Extra: Desplegar y plegar a lo loco y comprobar que sólo hay uno desplegado.
     */

    @Mock
    private lateinit var repository: ContentRepository
    @Mock
    private lateinit var getCachedContent: GetCachedContentUseCase
    @Mock
    private lateinit var getRemoteContent: GetRemoteContentUseCase
    @Mock
    private lateinit var saveContent: SaveContentUseCase
    private lateinit var contentStateFlow: MutableStateFlow<DataStatus<List<ContentUi>>>
    private lateinit var fragmentScenario: FragmentScenario<MainFragment>
    lateinit var contentModule: Module

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    /*@get:Rule
    val fragmentScenario = FragmentScenario.launchInContainer(MainFragment::class.java)*/

    /*@get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
    }*/

    @Before
    fun setup() {
        //TODO: Voy a tener que montar una fragmentTransaction...
        /*startKoin {
            androidLogger(Level.ERROR)
            modules(listOf(applicationModule, contentModule))
        }*/
        // FIXME: Ahora que funcionan los mocks quizá ya no necesito uno para cada interactor y me vale sólo con el del repository,
        //      como en el MainViewModelTest.
        repository = Mockito.mock(ContentRepository::class.java)
//        getCachedContent = Mockito.mock(GetCachedContentUseCase::class.java)
//        getRemoteContent = Mockito.mock(GetRemoteContentUseCase::class.java)
//        saveContent = Mockito.mock(SaveContentUseCase::class.java)
        getCachedContent = GetCachedContentUseCase(repository)
        getRemoteContent = GetRemoteContentUseCase(repository)
        saveContent = SaveContentUseCase(repository)
        //TODO: No sé si esto es necesario...
        contentModule = module {
            factory { getCachedContent }
            factory { getRemoteContent }
            factory { saveContent }
            viewModel { MainViewModel(get(), get(), get()) }
        }
        loadKoinModules(contentModule)
        /////////////////////////////////////
        contentStateFlow = MutableStateFlow(DataStatus.Empty(""))
        fragmentScenario = launchFragmentInContainer()
    }

    @Test
    fun clickListItemAndDeploySubitems() {
        //activityScenario.scenario.fragment
        //TODO: perform click en un item, check que el item está desplegado (unfolded)
        //fragmentScenario.moveToState(Lifecycle.State.RESUMED)
        /*fragmentScenario.onFragment {
            onData(withId(R.id.rv_categories))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CategoriesAdapter.CategoryViewHolder>(0, click()))
            assertTrue(true)
        }*/
        createMockContent()
        // FIXME: Mal! porque withId es de ViewMatchers y estoy mirando datos: 
        onData(withId(R.id.rv_categories))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CategoriesAdapter.CategoryViewHolder>(1, click()))
    }

    private fun createMockContent() = runBlocking {
        contentStateFlow.value = DataStatus.Loading()
        Mockito.`when`(getCachedContent.execute()).doReturn(TestFactoryUI.createContentList())
        contentStateFlow.value = DataStatus.Success(getCachedContent.execute())
    }

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.jg.videotest", appContext.packageName)
//    }

}