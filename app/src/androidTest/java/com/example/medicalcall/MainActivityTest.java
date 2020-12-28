package com.example.medicalcall;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Тестирование перехода по Activity
     */
    @Test
    public void allPacientsAndNotice() {

        Espresso.onView(ViewMatchers.withId(R.id.allPatientsBtn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.tablePacient)).check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)));
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.allNoticeBtn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.frCon)).check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)));


    }

}