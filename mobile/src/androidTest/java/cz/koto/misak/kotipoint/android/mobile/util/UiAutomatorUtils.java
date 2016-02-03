package cz.koto.misak.kotipoint.android.mobile.util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;


public class UiAutomatorUtils {


//    private static UiAutomatorUtils instance;
//
//    private UiAutomatorUtils() {
//    }
//
//    public static synchronized UiAutomatorUtils getInstance() {
//
//		/* Lazy initialization, creating object on first use */
//        if (instance == null) {
//            synchronized (UiAutomatorUtils.class) {
//                if (instance == null) {
//                    instance = new UiAutomatorUtils();
//                }
//            }
//        }
//
//        return instance;
//    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.
     */
    public static String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
