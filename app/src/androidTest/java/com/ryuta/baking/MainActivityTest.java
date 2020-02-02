package com.ryuta.baking;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ryuta.baking.activities.MainActivity;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ryuta.baking.TestUtils.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    // Make sure the recipe selection screen isn't empty.
    @Test
    public void listLoadedTest() {
        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_recipes),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
    }

    // Basic flow of moving though a recipe and its steps
    @Test
    public void mainActivityTest() {
        // Choose 2nd recipe
        ViewInteraction cardView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_recipes),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        cardView.perform(click());

        // Choose first step
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_steps),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3)),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        // Go to next step
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_next), withText("Next"),
                        childAtPosition(
                                allOf(withId(R.id.other),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Go to previous step
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_prev), withText("Prev"),
                        childAtPosition(
                                allOf(withId(R.id.other),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Back out to recipe selection screen
        pressBack();
        pressBack();

        cardView.check(matches(isDisplayed())); // should be back where we started
    }

    // Make sure the finish button brings us back to the step list screen.
    @Test
    public void finishButtonTest() {
        // Choose 1st recipe
        ViewInteraction cardView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_recipes),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        cardView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Choose the last step
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_steps),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3)),
                        6),
                        isDisplayed()));
        linearLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Tap the Finish Button
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_fin), withText("Finish"),
                        childAtPosition(
                                allOf(withId(R.id.other),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Make sure last step in step list screen is displayed again
        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rv_steps),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3)),
                        6),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));
    }
}
