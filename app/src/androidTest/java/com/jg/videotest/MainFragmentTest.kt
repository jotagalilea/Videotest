package com.jg.videotest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.adapter.CategoriesAdapter.CategoryViewHolder
import com.jg.videotest.ui.common.DataStatus
import com.jg.videotest.ui.main.MainActivity
import com.jg.videotest.ui.main.MainFragment
import com.jg.videotest.ui.main.MainViewModel
import com.jg.videotest.ui.main.PlayerFragment
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel

@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentTest {

    lateinit var mainFragmentScenario: FragmentScenario<MainFragment>
    private val LIST_ITEM = 1

    // Al parecer, esto no hace falta:
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        mainFragmentScenario = launchFragmentInContainer()
    }


    @Test
    fun showRecyclerView() {
        onView(withId(R.id.rv_categories)).check(ViewAssertions.matches(isDisplayed()))
    }

    //TODO: Faltarían los casos de click para cerrar categoría y de click en una categoría teniendo otra abierta.
    @Test
    fun clickCategoryAndUnfold() {
        onView(withId(R.id.rv_categories))
            .perform(actionOnItemAtPosition<CategoryViewHolder>(LIST_ITEM, click()))

        // Comprobación de si está desplegado:
        mainFragmentScenario.withFragment {
            val folded = (this.getSharedViewModel<MainViewModel>().getContentStateFlow().value as DataStatus.Success)
                .data[LIST_ITEM].folded
            assertTrue(!folded)
        }
    }

    // Esto debería probarlo en una clase específicamente para navegación:
    @Test
    fun clickUnfoldedVideoAndNavToPlayer() {
        //val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        onView(withId(R.id.rv_categories))
            .perform(actionOnItemAtPosition<CategoryViewHolder>(LIST_ITEM, click()))

        // Comprobación de si está desplegado:
        mainFragmentScenario.withFragment {
            // FIXME: No puedo usar el activityScenario porque me dice que se la ha cargado el test a estas alturas...
            //      Repasar cómo testear fragmentos con activityScenarioRule.
            println("Fragmento principal: " + activityScenarioRule.scenario.onActivity {
                it.navController.currentBackStackEntry!!.destination.displayName
            })
            // FIXME: Quizá deba esperar porque a lo mejor tarda en cargar los vídeos...
            //      Meter rule de timeout, por ejemplo.
            onView(withId(R.id.rv_categories))
                .perform(actionOnItemAtPosition<CategoryViewHolder>(LIST_ITEM, click()))
            println("Fragmento player: " + activityScenarioRule.scenario.onActivity {
                it.navController.currentBackStackEntry!!.destination.displayName
            })
        }
    }

}