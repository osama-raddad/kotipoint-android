package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageTransformation;
import timber.log.Timber;

public class GalleryViewModel extends BaseObservable {

    private Context mContext;
    private GalleryItem mGalleryItem;

    public GalleryViewModel(Context context, GalleryItem galleryItem) {
        this.mContext = context;
        this.mGalleryItem = galleryItem;
    }


    public String getImageUrl() {
        //"url":"localhost:8080/public/gallery/2015-11-15-Racice/racice_011.png"
        return "http://" + mGalleryItem.getUrl();
    }

    /**
     * https://medium.com/android-news/loading-images-with-data-binding-and-picasso-555dad683fdc#.1ny3d2s2w
     *
     * @param view
     * @param imageUrl
     */
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Timber.e("ImageUrl: %s",imageUrl);
        Picasso
                .with(view.getContext())
                .load(imageUrl)
//                .load("http://10.0.3.2:8080/public/gallery/2015-11-15-Racice/racice_001.png")
//                .load("http://localhost:8080/public/gallery/2015-11-15-Racice/racice_003.png")
                .transform(ImageTransformation.getTransformation(view))
                .placeholder(R.drawable.progress_animation)
                .into(view);
//        Uri imageURI = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(columnIndex));
//        Picasso
//                .with(context)
//                .load(imageURI)
//                .fit()
//                .centerInside()
//                .into(imageView);
    }

    public View.OnClickListener onClickGalleryItem() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = GalleryDetailActivity.newIntent(mContext);
                i.putExtra(GalleryDetailActivity.PAYLOAD_KEY, mGalleryItem);
                mContext.startActivity(i);
            }
        };
    }

}

