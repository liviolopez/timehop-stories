package com.liviolopez.timehopstories.other

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.liviolopez.timehopstories.utils.StringUtils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

// This test is performed like instrumentation test because "URLUtil.guessFileName" uses the app context
@RunWith(AndroidJUnit4::class)
class StringUtilsTest {

    @Test
    fun url_string_that_has_extension_mp4_returns_true(){
        val result = StringUtils.isVideoUrl(
            "https://splashbase.s3.amazonaws.com/mazwai/large/the_sea_also_rises_FKY.mp4"
        )

        assertTrue(result)
    }

    @Test
    fun url_string_that_has_extension_mp4_and_with_parameters_after_it_returns_true(){
        val result = StringUtils.isVideoUrl(
            "https://splashbase.s3.amazonaws.com/mazwai/large/travelpockets_iceland_land_of_fire_and_ice.mp4%3F1528191920"
        )

        assertTrue(result)
    }

    @Test
    fun url_string_that_has_an_extension_other_than_mp4_returns_false(){
        val result = StringUtils.isVideoUrl(
            "https://splashbase.s3.amazonaws.com/mazwai/large/the_sea_also_rises_FKY.jpg"
        )

        assertFalse(result)
    }

    @Test
    fun url_string_that_has_an_extension_other_than_mp4_and_parameters_returns_false(){
        val result = StringUtils.isVideoUrl(
            "https://splashbase.s3.amazonaws.com/mazwai/large/travelpockets_iceland_land_of_fire_and_ice.jpg%3F1528191920"
        )

        assertFalse(result)
    }
}