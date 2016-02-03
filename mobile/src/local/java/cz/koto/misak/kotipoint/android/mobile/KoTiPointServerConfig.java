package cz.koto.misak.kotipoint.android.mobile;


/**
 * KoTiPointServerConfig for local buildFlavour.
 * Don't forget update also other flavours!
 *
 * TODO would it be better to solve by using dependency injection (Dagger2 or so)?
 */
public class KoTiPointServerConfig extends KoTiPointBaseConfig
{

    //Google emulator
    //public static final String API_KOTINODE_IP = "10.0.2.2";

    //Genymotion emulator
    public static final String API_KOTINODE_IP = "10.0.3.2";

    public static final String API_KOTINODE_PORT = "8080";
    public static final String API_KOTINODE_PROTOCOL = "http";

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
