package com.android.footstamp

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.Shadows.shadowOf
import project.android.footstamp.data.login.GoogleLogin
import project.android.footstamp.ui.activity.LoginActivity

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var mockGoogleLogin: GoogleLogin

    @Before
    fun setUp() {
        // Hilt 초기화
        hiltRule.inject()

        // Mock 객체 초기화
        mockGoogleLogin = mock(GoogleLogin::class.java)
    }

    @Test
    fun testGoogleLoginEvent() {
        val intent = Intent()
        `when`(mockGoogleLogin.signInIntent).thenReturn(intent)

        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            activity.googleLoginEvent()

            verify(mockGoogleLogin).signInIntent
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assertNotNull(startedIntent)
            assertEquals(intent, startedIntent)
        }
    }
}