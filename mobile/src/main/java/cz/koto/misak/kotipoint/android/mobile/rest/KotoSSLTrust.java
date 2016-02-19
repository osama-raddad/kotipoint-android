package cz.koto.misak.kotipoint.android.mobile.rest;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import cz.koto.misak.kotipoint.android.mobile.KoTiPointBaseConfig;
import cz.koto.misak.kotipoint.android.mobile.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

public class KotoSSLTrust {
    public static OkHttpClient trustcert(Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        try {
            Resources rsc = context.getResources();
            String KOTO_SSL_PASS = rsc.getString(R.string.kotoSSLPass);

            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream instream = context.getResources().openRawResource(R.raw.kotokeystore);
            ksTrust.load(instream, KOTO_SSL_PASS.toCharArray());
            // TrustManager decides which certificate authorities to use.
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ksTrust);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(socketFactory);

            if (KoTiPointBaseConfig.DEV_API) {
                builder.addInterceptor(getHttpLoggingInterceptor());
            }

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            Timber.e(e,"Koto SSL Trust failed!");
        }

        return builder.build();
    }

    /**
     * Ensure logging of okhttp3.OkHttpClient
     * Add this as last interceptor!
     * @return
     */
    @NonNull
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
