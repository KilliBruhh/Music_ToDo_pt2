package com.example.musicapp_project_appdev;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.pm.ActivityInfo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

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
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        // Set the initial orientation to portrait before each test
        activityScenarioRule.getScenario().onActivity(activity ->
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
    }

    // TEst navigation form main to side activities
    @Test
    public void testSettingsActivity() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goAddSong)).perform(click());

        onView(withId(R.id.songName)).perform(replaceText("Test Song"));
        onView(withId(R.id.AlbumName)).perform(replaceText("Test Album"));
        onView(withId(R.id.durationSong)).perform(replaceText("111")).perform(closeSoftKeyboard());

        onView(withId(R.id.addSongButton)).perform(click());

    }
    @Test
    public void testAddMusicActivity() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goAddSong)).perform(click());
        // Check if Add song activity is now viewable
        onView(withId(R.id.addSongActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddSong() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.goAddSong)).perform(click());

        onView(withId(R.id.songName)).perform(replaceText("Test Song"));
        onView(withId(R.id.AlbumName)).perform(replaceText("Test Album"));
        onView(withId(R.id.durationSong)).perform(replaceText("111")).perform(closeSoftKeyboard());

        onView(withId(R.id.addSongButton)).perform(click());
    }

    // Test when u want to edit the editText fields are not empty
    @Test
    public void testUpdateFields() {
        // In Mainactivity clickin on items in Master fragmnet
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.listView)).perform(click());
        // In Detail Activity
        onView(withId(R.id.GoEditButton)).perform(click());
        // in Edit Activity
        onView(withId(R.id.songName)).perform(replaceText("Test Edit Song"));
        onView(withId(R.id.AlbumName)).perform(replaceText("Test Edit Album"));
        onView(withId(R.id.durationSong)).perform(replaceText("222")).perform(closeSoftKeyboard());
        // Confirming deletion
        onView(withText("Test Song")).check(matches(isDisplayed()));

    }

    // Test Delete
    @Test
    public void testDeleteSong() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.listView)).perform(click());

        onView(withId(R.id.GoEditButton)).perform(click());

        onView(withId(R.id.DeleteSongButton)).perform(click());
    }

    // Test change Lang
    @Test
    public void testLangChange() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(Settings.class);
        onView(withId(R.id.dutchButton)).perform(click());
        // Check if the greeting message is displayed correctly in the new language
        Espresso.onView(ViewMatchers.withId(R.id.settingsKop))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.settingsKop)));

    }
    // Test Change Theme
    @Test
    public void testThemeChange() {
        ActivityScenario mainActivityScenario = ActivityScenario.launch(Settings.class);
        onView(withId(R.id.darkButton)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.taalTextview))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.textColorDarkMode)));
    }

}