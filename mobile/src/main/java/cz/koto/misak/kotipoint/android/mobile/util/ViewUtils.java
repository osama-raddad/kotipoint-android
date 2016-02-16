package cz.koto.misak.kotipoint.android.mobile.util;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.View;

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

    public static void setViewHeight(View view, int height, boolean layout) {
        view.getLayoutParams().height = height;
        if (layout) {
            view.requestLayout();
        }
    }

}
