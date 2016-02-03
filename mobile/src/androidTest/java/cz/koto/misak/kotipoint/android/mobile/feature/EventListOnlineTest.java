package cz.koto.misak.kotipoint.android.mobile.feature;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.koto.misak.kotipoint.android.mobile.activity.MainActivity;

/**
 * Tests related to ONLINE interacting with {@link cz.koto.misak.kotipoint.android.mobile.fragment.EventListFragment}
 */
@RunWith(AndroidJUnit4.class)
public class EventListOnlineTest {


    /**
     * Launches {@link MainActivity} for every test
     */
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Test that recycler view contains at least given count of items.
     */
    @Test
    public void testDisplayEventList() {



    }
}
