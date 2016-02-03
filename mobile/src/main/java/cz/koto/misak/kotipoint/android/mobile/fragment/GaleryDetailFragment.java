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
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.ImageLayout;
import cz.koto.misak.kotipoint.android.mobile.view.Intents;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;

public class GaleryDetailFragment extends StatefulPermissionFragment {

    @Bind(R.id.image_preview)
    ImageLayout mImageLayout;

    protected GalleryItem mGalleryItem;

    public static GaleryDetailFragment newInstance(Context context, GalleryItem galleryItem) {
        Bundle b = new Bundle();
        b.putParcelable(Intents.EXTRA_IMAGE, galleryItem/*Parcels.wrap(image)*/);
        return (GaleryDetailFragment) GaleryDetailFragment.instantiate(context, GaleryDetailFragment.class.getName(), b);
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
        //TODO what is better this command or extractIntentArguments method?
//        mGalleryItem = getArguments().getParcelable(Intents.EXTRA_IMAGE);
        mGalleryItem = getActivity().getIntent().getExtras().getParcelable(GalleryDetailActivity.PAYLOAD_KEY);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void doWithMandatoryPermissions() {
        doPhotoView();
    }

    @Override
    protected void onSaveState(Bundle outState) {
        //TODO consider usage of save state (necessary with MVVM?)
        //outState.putParcelable(M_TODAY_WEATHER_WRAPPER_0784150041885340, mTodayWeatherWrapper);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        //TODO consider usage of save state (necessary with MVVM?)
//        mTodayWeatherWrapper = savedInstanceState.getParcelable(M_TODAY_WEATHER_WRAPPER_0784150041885340);
//        if ((isProgressLayoutVisible()||isContentLayoutVisible())&&mTodayWeatherWrapper!=null) {
//            bindView(getFragmentView().getContext());
//        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_gallery_detail;
    }

    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {

    }

    private void doPhotoView() {
        getFragmentView().showContent();
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
