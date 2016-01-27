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
import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageTransformation;

public class GalleryViewModel extends BaseObservable {

    private Context mContext;
    private GalleryItem mGalleryItem;

    public GalleryViewModel(Context context, GalleryItem galleryItem) {
        this.mContext = context;
        this.mGalleryItem = galleryItem;
    }


    public String getImageUrl() {
        //http://localhost:8080/public/gallery/2015-11-15-Racice/racice_001.png
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
        Picasso
                .with(view.getContext())
                //http://localhost:8080/public/gallery/2015-11-15-Racice/racice_001.png
                .load(imageUrl)
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

