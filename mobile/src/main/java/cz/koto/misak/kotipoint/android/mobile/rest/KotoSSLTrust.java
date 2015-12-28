package cz.koto.misak.kotipoint.android.mobile.rest;


import android.content.Context;
import android.content.res.Resources;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;

public class KotoSSLTrust {
    public static OkHttpClient trustcert(Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
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
            okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            Logcat.e(e,"Koto SSL Trust failed!");
        }
        return okHttpClient;
    }
}
