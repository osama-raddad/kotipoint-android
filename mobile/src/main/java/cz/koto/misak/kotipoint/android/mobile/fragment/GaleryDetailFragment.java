package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageLayout;
import cz.koto.misak.kotipoint.android.mobile.view.Intents;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;

public class GaleryDetailFragment extends StatefulPermissionFragment {

    protected GalleryItem mGalleryItem;

    @Bind(R.id.image_preview)
    ImageLayout mImageLayout;

    public static GaleryDetailFragment newInstance(Context context, GalleryItem galleryItem) {
        Bundle b = new Bundle();
        b.putParcelable(Intents.EXTRA_IMAGE, galleryItem/*Parcels.wrap(image)*/);
        return (GaleryDetailFragment) GaleryDetailFragment.instantiate(context, GaleryDetailFragment.class.getName(), b);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        extractIntentArguments();
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO what is better this command or extractIntentArguments method?
        mGalleryItem = getActivity().getIntent().getExtras().getParcelable(GalleryDetailActivity.PAYLOAD_KEY);

        /*
         * Request all permissions defined in getPermissionList and
         * call doWithPermissions() since all of them are granted.
         * Call permissionNotGranted otherwise.
         */
        requestPermissions();
    }

    @Override
    StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void doWithPermissions() {
        doPhotoView();
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_gallery_detail;
    }

    @Override
    void initView(View view, Bundle savedInstanceState) {

    }

    private void extractIntentArguments() {
        Bundle b = getArguments();
        mGalleryItem = /*Parcels.unwrap(b.getParcelable(Intents.EXTRA_IMAGE)*/b.getParcelable(Intents.EXTRA_IMAGE);
    }

    private void doPhotoView() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Picasso.with(getContext())
                .load("http://"+mGalleryItem.getUrl())
                .resize(width, height)
                .centerCrop().into(mImageLayout);
    }
}
