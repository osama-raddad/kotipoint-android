package cz.koto.misak.kotipoint.android.mobile;


/**
 * KoTiPointServerConfig for rosti buildFlavour.
 * Don't forget update also other flavours!
 *
 * TODO would it be better to solve by using dependency injection (Dagger2 or so)?
 */
public class KoTiPointServerConfig extends KoTiPointBaseConfig
{
	public static final String API_KOTINODE_PROTOCOL = "https";

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
