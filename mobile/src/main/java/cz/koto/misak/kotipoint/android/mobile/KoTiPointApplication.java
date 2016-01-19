package cz.koto.misak.kotipoint.android.mobile;

import android.app.Application;


public class KoTiPointApplication extends Application {

    private static KoTiPointApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        //Use fabric for non-dev api only.
//        CrashlyticsCore core = new CrashlyticsCore.Builder()
//                .disabled(KoTiPointConfig.DEV_API)
//                .build();
        /**
         * Attention!
         * Never let the Fabric to generate plain new Crashlytics() Kit!
         * Always use customized Kit (like the core above) to omit DEV reporting to Crashlytics server.
         */
//        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    public static KoTiPointApplication get() {
        return sInstance;
    }
}
