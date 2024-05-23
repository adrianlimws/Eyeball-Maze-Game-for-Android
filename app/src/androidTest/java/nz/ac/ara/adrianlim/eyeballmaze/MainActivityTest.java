package nz.ac.ara.adrianlim.eyeballmaze;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.anything;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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
    public void testLevelDetailsTextView() {
        onView(withId(R.id.text_maze_level)).check(matches(withText("Level 1")));
        onView(withId(R.id.text_elapsed_time)).check(matches(withText("Time: 00:00")));
        onView(withId(R.id.text_move_count)).check(matches(withText("Moves: 0")));
        onView(withId(R.id.text_goal_count)).check(matches(withText("Goal: 0/1")));
    }

    @Test
    public void testGameRulesTextView() {
        ViewInteraction gridView = onView(withId(R.id.grid_game_level));
        gridView.perform(click());
        onView(withId(R.id.text_rule_dialog)).check(matches(withText("Select a tile to make a move")));
    }

    @Test
    public void testBottomNavigationBar() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        onView(withId(R.id.action_sound))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.sound)));

        onView(withId(R.id.action_undo))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.undo)));

        onView(withId(R.id.action_pause))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.pause)));

        onView(withId(R.id.action_rules))
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(R.string.rules)));
    }

    @Test
    public void testLevelGridViewDisplayed() {
        // Check if the level GridView is displayed
        onView(withId(R.id.grid_game_level))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUndoButton() {
        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(13)
                .perform(click());

        onView(withId(R.id.action_undo))
                .perform(click());

        onView(withId(R.id.text_move_count))
                .check(matches(withText("Moves: 0")));
    }

    @Test
    public void testPauseButton() {
        onView(withId(R.id.action_pause))
                .perform(click());

        onView(withText("Game Paused"))
                .check(matches(isDisplayed()));

        onView(withText("Continue"))
                .perform(click());

        onView(withId(R.id.grid_game_level))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRulesVideoDialog() {
        onView(withId(R.id.action_rules))
                .perform(click());

        onView(withId(R.id.videoView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.closeButton))
                .perform(click());

        onView(withId(R.id.videoView))
                .check(doesNotExist());
    }

    /* To test the gridView level, picture the levelLayout as a flatten array (from line 93 of MainActivity.java)
     * [0, 0, 11, 0, 1, 12, 8, 2, 10, 15, 14, 8, 11, 9, 15, 10, 13, 7, 9, 5, 0, 5, 0, 6]
     *  0  1  2   3  4  5   6  7  8   9   10  11 12  13 14  15  16  17 18 19 20 21 22 23
     * */
    @Test
    public void testValidTileClick() {
        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(17)
                .perform(click());

        onView(withId(R.id.text_move_count))
                .check(matches(withText("Moves: 1")));
    }

    @Test
    public void testCannotMoveDiagonally() {
        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.text_rule_dialog))
                .check(matches(withText("Cannot move diagonally")));
    }

    @Test
    public void testGameOverDialogNoLegalMoves() {

        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(17)
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(19)
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.grid_game_level))
                .atPosition(23)
                .perform(click());

        onView(withText("Game Over"))
                .check(matches(isDisplayed()));

        onView(withText("You lost as there are no legal moves to make."))
                .check(matches(isDisplayed()));

        onView(withText("Restart"))
                .perform(click());

        onView(withId(R.id.text_maze_level)).check(matches(withText("Level 1")));
        onView(withId(R.id.text_elapsed_time)).check(matches(withText("Time: 00:00")));
        onView(withId(R.id.text_move_count)).check(matches(withText("Moves: 0")));
        onView(withId(R.id.text_goal_count)).check(matches(withText("Goal: 0/1")));
    }
}