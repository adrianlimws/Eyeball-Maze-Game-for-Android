package nz.ac.ara.adrianlim.eyeballmaze;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testInitialState() {
        onView(withId(R.id.text_maze_level)).check(matches(withText("Level 1")));
        onView(withId(R.id.text_elapsed_time)).check(matches(withText("Time: 00:00")));
        onView(withId(R.id.text_move_count)).check(matches(withText("Moves: 0")));
        onView(withId(R.id.text_goal_count)).check(matches(withText("Goal: 0/1")));
    }

    @Test
    public void testInvalidMove() {
        ViewInteraction gridView = onView(withId(R.id.grid_game_level));
        gridView.perform(click());
        onView(withId(R.id.text_rule_dialog)).check(matches(withText("Select a tile to make a move")));
    }

    @Test
    public void testBottomNavigationBar() {
        // Check if the bottom navigation view exists
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        // Check if the sound button exists and has the correct icon
        onView(withId(R.id.action_sound))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.sound)))
                .check(matches(withDrawable(R.drawable.icon_sound_on)));

        // Check if the undo button exists and has the correct icon
        onView(withId(R.id.action_undo))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.undo)))
                .check(matches(withDrawable(R.drawable.icon_undo)));

        // Check if the pause button exists and has the correct icon
        onView(withId(R.id.action_pause))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.pause)))
                .check(matches(withDrawable(R.drawable.icon_pause)));

        // Check if the rules button exists and has the correct icon
        onView(withId(R.id.action_rules))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.rules)))
                .check(matches(withDrawable(R.drawable.icon_rules)));
    }
}