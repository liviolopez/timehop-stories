package com.liviolopez.timehopstories.ui.source

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories._components.launchFragmentInHiltContainer
import com.liviolopez.timehopstories.base.Constants
import com.liviolopez.timehopstories.di.RemoteParamsModule
import com.liviolopez.timehopstories.ui._components.AppFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton


@SmallTest
@UninstallModules(RemoteParamsModule::class)
@HiltAndroidTest
class SourceFragmentTest_RealAPI {

    @Module
    @InstallIn(SingletonComponent::class)
    object RemoteParamsModule {

        @Singleton
        @Provides
        fun provideBaseUrl(): String {
            return Constants.BASE_URL
        }

    }

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<SourceFragment>(
            factory = fragmentFactory, fragmentArgs = bundleOf(
                "sourceId" to 1801
            )
        )
    }

    @Test
    fun is_Url_source_the_same_that_return_the_api(){
        Thread.sleep(3000)
        onView(withId(R.id.tv_url)).check(ViewAssertions.matches(withText("http://mazwai.com/#226")))
    }

    @Test
    fun is_the_Fragment_title_the_Source_name_that_return_the_api(){
        Thread.sleep(3000)
        onView(withId(R.id.action_bar)).check(ViewAssertions.matches(hasDescendant(withText("the sea also rises FKY.png?1506949636"))))
    }
}