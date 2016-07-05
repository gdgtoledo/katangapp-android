package es.craftsmanship.toledo.katangapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import es.craftsmanship.toledo.katangapp.activities.MainActivity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristóbal Hermida
 */
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    public ApplicationTest() {
        super();
    }

    @Test
    public void titleIsDisplayed() throws Exception {
        Thread.sleep(5000);

        onView(withText("katanga")).check(matches(isDisplayed()));
    }

}