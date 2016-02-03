package cz.koto.misak.kotipoint.android.mobile;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class KoTiPointApplication extends Application {

    private static KoTiPointApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Timber.plant(new Timber.DebugTree());

        //Use fabric for non-dev api only.
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(KoTiPointBaseConfig.DEV_API)
                .build();
        /**
         * Attention!
         * Never let the Fabric to generate plain new Crashlytics() Kit!
         * Always use customized Kit (like the core above) to omit DEV reporting to Crashlytics server.
         */
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    public static KoTiPointApplication get() {
        return sInstance;
    }
}
