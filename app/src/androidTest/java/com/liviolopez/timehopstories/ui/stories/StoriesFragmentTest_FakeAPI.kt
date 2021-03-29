package com.liviolopez.timehopstories.ui.stories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories._components.FileReader
import com.liviolopez.timehopstories._components.launchFragmentInHiltContainer
import com.liviolopez.timehopstories.ui._components.AppFragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class StoriesFragmentTest_FakeAPI{

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        launchFragmentInHiltContainer<StoriesFragment>(factory = fragmentFactory)
    }

    @After
    fun tearDown() = mockWebServer.shutdown()

    @Test
    fun is_OverlayStandByView_on_initial_status(){
        onView(withId(R.id.progress_bar_container)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.error_container)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.empty_list)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun are_image_and_video_show_hide_on_viewpager(){

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("splashbase.latest.response.json"))
            }
        }

        Thread.sleep(500)
        onView(withId(R.id.bt_next)).perform(ViewActions.click())
        onView(withId(R.id.iv_item_picture)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.vv_item_video)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))

        Thread.sleep(500)
        onView(withId(R.id.bt_next)).perform(ViewActions.click())
        onView(withId(R.id.iv_item_picture)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.vv_item_video)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))

        Thread.sleep(500)
        onView(withId(R.id.bt_next)).perform(ViewActions.click())
        onView(withId(R.id.iv_item_picture)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.vv_item_video)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))

        Thread.sleep(500)
        onView(withId(R.id.bt_next)).perform(ViewActions.click())
        onView(withId(R.id.iv_item_picture)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.vv_item_video)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.vp_story)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}
