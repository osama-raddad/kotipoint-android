package cz.koto.misak.kotipoint.android.mobile.util;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;

public class ViewUtils {

    public static float convertPixelsToDp(float px, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static void setUpDetailActionBar(ActionBar actionBar){
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//back arrow button
            actionBar.setDisplayShowHomeEnabled(true);

            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

}
