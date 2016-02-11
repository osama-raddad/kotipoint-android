package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentGalleryDetailBinding;
import cz.koto.misak.kotipoint.android.mobile.fragment.base.StatefulPermissionFragment;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.view.Intents;
import cz.koto.misak.kotipoint.android.mobile.viewModel.GalleryViewModel;

public class GaleryDetailFragment extends StatefulPermissionFragment {

    private FragmentGalleryDetailBinding mBinding;
    private GalleryViewModel mViewModel;

    protected GalleryItem mGalleryItem;

    public static GaleryDetailFragment newInstance(Context context, GalleryItem galleryItem) {
        Bundle b = new Bundle();
        b.putParcelable(Intents.EXTRA_IMAGE, galleryItem/*Parcels.wrap(image)*/);
        return (GaleryDetailFragment) GaleryDetailFragment.instantiate(context, GaleryDetailFragment.class.getName(), b);
    }

    @Override
    public void doWithMandatoryPermissions() {
        showContent();
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
        mBinding = FragmentGalleryDetailBinding.bind(view);
        mViewModel = new GalleryViewModel(getActivity().getIntent().getExtras().getParcelable(GalleryDetailActivity.PAYLOAD_KEY));
        mBinding.setGalleryViewModel(mViewModel);
    }

    @Override
    public void reloadFragmentView() {
        requestContent();
    }

}
