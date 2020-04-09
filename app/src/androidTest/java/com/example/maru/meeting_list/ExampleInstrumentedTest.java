package com.example.maru.meeting_list;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.MainActivity;
import com.example.maru.R;
import com.example.maru.utils.DeleteViewAction;
import com.example.maru.utils.RecyclerViewItemAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import model.Meeting;
import service.MeetingApiService;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static int ITEMS_COUNT = 3;

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * Nous nous assurons que notre aperçu du recyclage s'affiche au moins sur l'article
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.fragment_main_recycler_view))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     *  Lorsque nous supprimons un élément, celui-ci n'est plus affiché
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {

        onView(withId(R.id.fragment_main_recycler_view)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.fragment_main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(withId(R.id.fragment_main_recycler_view)).check(withItemCount(ITEMS_COUNT-1));
    }


    @Test
    public void testItemDateAndRoom() {

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Trier par date"),
                        isDisplayed()));
        appCompatTextView.perform(click());
        onView(withId(R.id.fragment_main_recycler_view)).check( new RecyclerViewItemAssertion( 0,R.id.text_info, withText("Réunion C - 19h00 - Luigi")));


        ViewInteraction menuButton = onView(
                allOf(withContentDescription("More options"),
                        isDisplayed()));
        menuButton.perform(click());

        ViewInteraction compatTextView = onView(
                allOf(withId(R.id.title), withText("Trier par salle"),
                        isDisplayed()));
        compatTextView.perform(click());
        onView(withId(R.id.fragment_main_recycler_view)).check( new RecyclerViewItemAssertion( 0,R.id.text_info, withText("Réunion A - 14h00 - Peach")));
    }

    @Test
    public void addMeeting() {

        onView(withId(R.id.floatingButton)).perform(ViewActions.click());
        onView(withId(R.id.textView)).check(matches(withText("Choisir l'importance de la réunion")));

        onView(withId(R.id.timePicker)).perform(PickerActions.setTime(10,0));
        onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2020,2,4));

        onView(withId(R.id.lieuReunion)).perform(replaceText("Réunion C"));
        onView(withId(R.id.sujetRéunion)).perform(replaceText("Yoshi"));

        onView(withId(R.id.buttonOK)).perform(ViewActions.click());

        onView(withId(R.id.fragment_main_recycler_view)).check(withItemCount(ITEMS_COUNT + 1));
        onView(withId(R.id.fragment_main_recycler_view)).check( new RecyclerViewItemAssertion( 3,R.id.text_info, withText("Réunion C - 10h00 - Yoshi")));
    }
}
