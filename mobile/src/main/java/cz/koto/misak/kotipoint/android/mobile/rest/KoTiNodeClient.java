package cz.koto.misak.kotipoint.android.mobile.rest;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import cz.koto.misak.kotipoint.android.mobile.KoTiPointConfig;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class KoTiNodeClient {

    private static KoTiNodeApi restInterface;

    public static KoTiNodeApi getKoTiNodeClient(Context context) {

        if (restInterface == null) {
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");//ISO-8601
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KoTiPointConfig.API_KOTINODE_ENDPOINT_PRODUCTION)
                    .client(KotoSSLTrust.trustcert(context))
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//important for RX!!!
                    .build();

            restInterface =  retrofit.create(KoTiNodeApi.class);
        }
        return restInterface;
    }
}