package cz.koto.misak.kotipoint.android.mobile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cz.koto.misak.kotipoint.android.mobile.feature.EventListOnlineTest;

/**
 * Runs all instrumentation tests for online state from one place
 */
@RunWith (Suite.class)
@Suite.SuiteClasses ({
		EventListOnlineTest.class
})
public class OnlineTestSuite {
}
