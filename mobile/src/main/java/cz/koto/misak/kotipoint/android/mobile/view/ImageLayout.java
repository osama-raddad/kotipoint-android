package cz.koto.misak.kotipoint.android.mobile.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageLayout extends RelativeLayout implements Target {

    public ImageLayout(Context context) {
        super(context);
    }

    public ImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        //TODO provide button for reload possibility?
        //setBackground(new ColorDrawable(Color.RED));//Set your error drawable
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //TODO display static icon about loading?
        //setBackground(new ColorDrawable(Color.GREEN));//Set your placeholder
    }
}
