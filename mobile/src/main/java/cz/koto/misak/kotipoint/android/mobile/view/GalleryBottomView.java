package cz.koto.misak.kotipoint.android.mobile.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class GalleryBottomView extends LinearLayout {
    public GalleryBottomView(Context context) {
        super(context);
    }

    public GalleryBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();

        int hms = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, hms);

        int unspecifiedHeight = getMeasuredHeight();
        if (unspecifiedHeight > height) {
            height = unspecifiedHeight;
        }

        setMeasuredDimension(getMeasuredWidth(), height);
    }
}
