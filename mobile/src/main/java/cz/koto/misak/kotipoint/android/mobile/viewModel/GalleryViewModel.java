package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageTransformation;
import timber.log.Timber;

/**
 * Subclassing BaseObservable allows us to use @Bindable on getters, and notifyPropertyChanged() when a @Bindable property changes.
 */
public class GalleryViewModel extends BaseObservable {

    private static final String PROTOCOL_PREFIX = "http://";

    private GalleryItem mGalleryItem;

    public GalleryViewModel(GalleryItem galleryItem) {
        this.mGalleryItem = galleryItem;
    }


    public String getImageUrl() {
        return PROTOCOL_PREFIX + mGalleryItem.getUrl();
    }

    /**
     * https://medium.com/android-news/loading-images-with-data-binding-and-picasso-555dad683fdc#.1ny3d2s2w
     *
     * @param view
     * @param imageUrl
     */
    @BindingAdapter({"bind:imageUrl"})
    public static void imageUrl(ImageView view, String imageUrl) {
        Timber.e("ImageUrl: %s", imageUrl);
        Picasso
                .with(view.getContext())
                .load(imageUrl)
                .transform(ImageTransformation.getTransformation(view))
                .into(view);
    }


    @BindingAdapter({"bind:imageUrlDetail"})
    public static void imageUrlDetail(ImageView view, String imageUrl) {
        /**
         * //TODO consider offer to the user switch between center and crop.
         * //TODO or consider offer to the user scale image when crop is chosen.
         */
        Context context = view.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Picasso.with(context)
                .load(imageUrl)
                .resize(width, height)
                .centerCrop()
//                .centerInside()
                .into(view);
    }

    public View.OnClickListener callGalleryDetailActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent i = GalleryDetailActivity.newIntent(context);
                i.putExtra(GalleryDetailActivity.PAYLOAD_KEY, mGalleryItem);
                context.startActivity(i);
            }
        };
    }

}

