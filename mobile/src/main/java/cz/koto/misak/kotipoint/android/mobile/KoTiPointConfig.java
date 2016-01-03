package cz.koto.misak.kotipoint.android.mobile;


public class KoTiPointConfig
{

	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_API = BuildConfig.DEV_API;

    /**
     * Localhost endpoint
     */

    // Google emulator
    public static final String API_KOTINODE_ENDPOINT_DEV_EMULATOR = "http://10.0.2.2:8080";
    // Genymotion emulator
    public static final String API_KOTINODE_ENDPOINT_DEV_VBOXNET = "http://10.0.3.2:8080";

	/**
	 * Internet endpoint
	 */
	public static final String API_KOTINODE_ENDPOINT_PRODUCTION = "https://kotopeky.cz/api/kotinode";

	/**
	 * Delay (in millis) for testing purpose.
	 */
	public static final int API_KOTINODE_TEST_DELAY = 2000;
}
