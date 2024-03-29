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

import java.util.HashMap;
import java.util.Map;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageTransformation;
import timber.log.Timber;

/**
 * Subclassing BaseObservable allows us to use @Bindable on getters, and notifyPropertyChanged() when a @Bindable property changes.
 */
public class GalleryViewModel extends BaseObservable {

    public static final String PAYLOAD_MAP_KEY = "koTiGalleryDetailPayloadMap";
    public static final String PAYLOAD_POINTER_KEY = "koTiGalleryDetailPayloadPointer";

    private static final String PROTOCOL_PREFIX = "http://";

    private HashMap<Long,GalleryItem> mGalleryItemMap = new HashMap<>();
    private Long mGalleryItemPointer;

//    public GalleryViewModel(GalleryItem galleryItem) {
//        this.mGalleryItemMap.clear();
//        this.mGalleryItemMap.put(galleryItem.getId(),galleryItem);
//        this.mGalleryItemPointer = galleryItem.getId();
//    }

    public GalleryViewModel(Map<Long,GalleryItem> galleryItemMap, Long currentItem){
        if (galleryItemMap!=null) {
            this.mGalleryItemMap.putAll(galleryItemMap);
            this.mGalleryItemPointer = currentItem;
        }
    }


    public String getImageUrl() {
        GalleryItem galleryItem = mGalleryItemMap.get(mGalleryItemPointer);
        if (galleryItem==null){
            //This is serious mistake, log crash and use placeholder for image
            Timber.e("Missing gallery item on position: %s! Placeholder will be used.",mGalleryItemPointer);
            Timber.wtf("mGalleryItemMap=[%s]",mGalleryItemMap);
            return "android.resource://cz.koto.misak.kotipoint.android.mobile/"+R.drawable.detail_kotopeky;
        }
        return PROTOCOL_PREFIX + galleryItem.getUrl();
    }

    /**
     * https://medium.com/android-news/loading-images-with-data-binding-and-picasso-555dad683fdc#.1ny3d2s2w
     *
     * @param view
     * @param imageGalleryUrl
     */
    @BindingAdapter({"bind:imageGalleryUrl"})
    public static void loadImageGalleryView(ImageView view, String imageGalleryUrl) {
        Timber.d("loadImageGalleryView(): %s", imageGalleryUrl);
        Picasso
                .with(view.getContext())
                .load(imageGalleryUrl)
                .transform(ImageTransformation.getTransformation(view))
                .into(view);
    }


    @BindingAdapter({"bind:imageGalleryDetailUrl"})
    public static void loadImageGalleryDetailView(ImageView view, String imageGalleryDetailUrl) {
        Timber.d("loadImageGalleryDetailView(): %s", imageGalleryDetailUrl);
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
                .load(imageGalleryDetailUrl)
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
                i.putExtra(GalleryViewModel.PAYLOAD_MAP_KEY, mGalleryItemMap);
                i.putExtra(GalleryViewModel.PAYLOAD_POINTER_KEY, mGalleryItemPointer);
                context.startActivity(i);
            }
        };
    }

}

