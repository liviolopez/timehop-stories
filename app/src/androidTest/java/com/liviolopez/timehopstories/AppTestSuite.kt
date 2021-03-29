package com.liviolopez.timehopstories

import com.liviolopez.timehopstories.other.StringUtilsTest
import com.liviolopez.timehopstories.other.ThemeColorTest
import com.liviolopez.timehopstories.ui.MainActivityTest
import com.liviolopez.timehopstories.ui.source.SourceFragmentTest_FakeAPI
import com.liviolopez.timehopstories.ui.source.SourceFragmentTest_RealAPI
import com.liviolopez.timehopstories.ui.stories.StoriesFragmentTest_FakeAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    StoriesFragmentTest_FakeAPI::class,
    SourceFragmentTest_FakeAPI::class,
    SourceFragmentTest_RealAPI::class,
    ThemeColorTest::class,
    StringUtilsTest::class
)
class AppTestSuite