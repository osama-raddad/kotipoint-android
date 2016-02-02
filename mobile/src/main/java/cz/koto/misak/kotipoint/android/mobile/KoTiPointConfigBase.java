package cz.koto.misak.kotipoint.android.mobile;


/**
 * KoTiPointConfigBase is base class for KoTiPointConfig given by specific flavour.
 * TODO this would be nice to solve by using dependency injection (Dagger2 or so)
 */
public class KoTiPointConfigBase
{

	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_API = BuildConfig.DEV_API;

	/**
	 * Delay (in millis) for testing purpose.
	 */
	public static final int API_KOTINODE_TEST_DELAY = 2000;

}
