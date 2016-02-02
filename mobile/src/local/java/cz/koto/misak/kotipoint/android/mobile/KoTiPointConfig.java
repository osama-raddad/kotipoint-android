package cz.koto.misak.kotipoint.android.mobile;


public class KoTiPointConfig extends KoTiPointConfigBase
{

	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_API = BuildConfig.DEV_API;

    //Google emulator
    //public static final String API_KOTINODE_IP = "10.0.2.2";

    //Genymotion emulator
    public static final String API_KOTINODE_IP = "10.0.3.2";

    public static final String API_KOTINODE_PORT = "8080";
    public static final String API_KOTINODE_PROTOCOL = "http";

    // Genymotion emulator
    public static final String API_KOTINODE_ENDPOINT = API_KOTINODE_PROTOCOL+"://"+API_KOTINODE_IP+":"+API_KOTINODE_PORT;


    /**
     * LocalFlavour related url replacement.
     * @param urlString
     * @return
     */
    public static String replaceUrl(String urlString){
        if (urlString==null) return urlString;
        return urlString.replace("localhost",API_KOTINODE_IP);
    }


}
