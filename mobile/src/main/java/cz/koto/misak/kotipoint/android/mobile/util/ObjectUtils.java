package cz.koto.misak.kotipoint.android.mobile.util;

import java.util.HashMap;
import java.util.Map;


public class ObjectUtils {


    /**
     * Use this deepCopy when there is some NON-immutable objects in map.
     * <br/><br/>
     * Map<Integer, Map<String, Object>> original=new HashMap<Integer, Map<String,Object>>();
     * Map<Integer, Map<String, Object>> copy = deepCopy(original);
     * <br/><br/>
     * @param original
     * @param <K1>
     * @param <K2>
     * @param <V>
     * @return
     *
     *
     */
    public static <K1, K2, V> Map<K1, Map<K2, V>> deepCopy(
            Map<K1, Map<K2, V>> original){

        Map<K1, Map<K2, V>> copy = new HashMap<K1, Map<K2, V>>();
        for(Map.Entry<K1, Map<K2, V>> entry : original.entrySet()){
            copy.put(entry.getKey(), new HashMap<K2, V>(entry.getValue()));
        }
        return copy;
    }

    /**
     * Wrapper for default copyConstructor for immutable objects in map.
     * (Immutable e.g. Integer and other Number types, Boolean, String, are ok, but not Collections, Dates, Maps, Arrays etc.)
     * For non-immutable objects use other copy method (e.g. deepCopy).
     * @param original
     * @param <K1>
     * @param <V>
     * @return
     */
    public static <K1, V> Map<K1, V>  deepCopyForImmutables(HashMap<K1,V> original){
        Map<K1, V> copy = new HashMap<K1, V>(original);
        return copy;
    }
}
