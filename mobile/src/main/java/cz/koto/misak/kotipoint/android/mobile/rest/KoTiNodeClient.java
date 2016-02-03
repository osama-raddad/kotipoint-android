package cz.koto.misak.kotipoint.android.mobile.rest;

import android.content.Context;

import com.google.gson.GsonBuilder;

import cz.koto.misak.kotipoint.android.mobile.KoTiPointServerConfig;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class KoTiNodeClient {

    private static KoTiNodeApi sRestInterface;

    public static KoTiNodeApi getKoTiNodeClient(Context context) {

        if (sRestInterface == null) {
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");//ISO-8601
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KoTiPointServerConfig.API_KOTINODE_ENDPOINT)
                    .client(KotoSSLTrust.trustcert(context))
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//important for RX!!!
                    .build();

            sRestInterface =  retrofit.create(KoTiNodeApi.class);
        }
        return sRestInterface;
    }
}