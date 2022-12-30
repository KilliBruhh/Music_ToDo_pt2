package com.example.musicapp_project_appdev;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /*
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.musicapp_project_appdev", appContext.getPackageName());
    }
    */


    // TEst navigation form main to side activities
    // app closes at end of test --> Is this normal? (no error message in test logs)
    // Works

    @Test
    public void testSettingsActivity() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goSettings)).perform(click());
        // Check if Settings activity is now viewable
        onView(withId(R.id.backgroundId)).check(matches(isDisplayed()));
    }
    @Test
    public void testAddMusicActivity() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goAddSong)).perform(click());
        // Check if Settings activity is now viewable
        onView(withId(R.id.addSongActivity)).check(matches(isDisplayed()));
    }

    // Test when u want to edit the editText fields are not empty
    public void testUpdateFields() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goSettings)).perform(click());
        // Check if Settings activity is now viewable
        onView(withId(R.id.backgroundId)).check(matches(isDisplayed()));
    }
}