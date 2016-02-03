package cz.koto.misak.kotipoint.android.mobile;


/**
 * KoTiPointConfigBase is base config for application.
 */
public class KoTiPointBaseConfig
{

	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_API = BuildConfig.DEV_API;

	/**
	 * Delay (in millis) for testing purpose.
	 */
	public static final int API_KOTINODE_TEST_DELAY = 2000;

}
