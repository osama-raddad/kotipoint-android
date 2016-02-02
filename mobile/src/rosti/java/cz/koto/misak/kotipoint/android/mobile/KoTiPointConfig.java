package cz.koto.misak.kotipoint.android.mobile;


public class KoTiPointConfig extends KoTiPointConfigBase
{
	public static final String API_KOTINODE_PROTOCOL = "https";

	// Genymotion emulator
	public static final String API_KOTINODE_ENDPOINT;

    static {
        API_KOTINODE_ENDPOINT = API_KOTINODE_PROTOCOL + "://kotopeky.cz/api/kotinode";
    }


    /**
     * RostiFlavour related alternative for localFlavour url replacement.
     * @param urlString
     * @return
     */
    public static String replaceUrl(String urlString){
        return urlString;
    }
}
