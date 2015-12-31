package cz.koto.misak.kotipoint.android.mobile;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;


public class KoTiPointApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Use fabric for non-dev api only.
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(KoTiPointConfig.DEV_API)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

}
