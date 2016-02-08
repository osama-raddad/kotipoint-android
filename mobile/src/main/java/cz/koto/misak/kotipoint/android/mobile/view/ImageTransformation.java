package cz.koto.misak.kotipoint.android.mobile.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import timber.log.Timber;

public class ImageTransformation {

    public static Transformation getTransformation(final ImageView imageView) {
        return new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                try {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;

                }catch (Throwable th){
                    //TODO explore cause of fail in unset width & height (java.lang.IllegalArgumentException: width and height must be > 0)
                    Timber.e(th, "Unable to ......");
                    return source;
                }
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }
}