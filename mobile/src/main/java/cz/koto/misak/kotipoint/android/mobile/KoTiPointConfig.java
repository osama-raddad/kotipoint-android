package cz.koto.misak.kotipoint.android.mobile;


public class KoTiPointConfig
{

	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_API = BuildConfig.DEV_API;

    /**
     * Localhost endpoint
     */

    //using Google emulator
    public static final String API_KOTINODE_ENDPOINT_DEV_EMULATOR = "http://10.0.2.2:8080";
    //using Genymotion emulator
    public static final String API_KOTINODE_ENDPOINT_DEV_VBOXNET = "http://10.0.3.2:8080";

	/**
	 * Internet endpoint
	 */
	public static final String API_KOTINODE_ENDPOINT_PRODUCTION = "https://kotopeky.cz/api/kotinode";
	public static final String API_TYPICODE_ENDPOINT_PROD = "http://jsonplaceholder.typicode.com/";
}
