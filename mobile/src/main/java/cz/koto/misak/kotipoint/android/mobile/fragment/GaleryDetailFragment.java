package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentGalleryDetailBinding;
import cz.koto.misak.kotipoint.android.mobile.fragment.base.StatefulPermissionFragment;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.viewModel.GalleryViewModel;
import timber.log.Timber;

public class GaleryDetailFragment extends StatefulPermissionFragment {

    private FragmentGalleryDetailBinding mBinding;
    private GalleryViewModel mViewModel;

    protected Long mGalleryPointer;

    @Bind(R.id.image_preview)
    ImageView mImagePreview;

    public static GaleryDetailFragment newInstance(Context context, Long galleryPointer) {
        Bundle b = new Bundle();
        b.putLong(GalleryViewModel.PAYLOAD_POINTER_KEY, galleryPointer);
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
        outState.putLong(GalleryViewModel.PAYLOAD_POINTER_KEY, mGalleryPointer);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        //TODO consider usage of save state (necessary with MVVM?)
//        mTodayWeatherWrapper = savedInstanceState.getParcelable(M_TODAY_WEATHER_WRAPPER_0784150041885340);
//        if ((isProgressLayoutVisible()||isContentLayoutVisible())&&mTodayWeatherWrapper!=null) {
//            bindView(getFragmentView().getContext());
//        }
        mGalleryPointer = savedInstanceState.getLong(GalleryViewModel.PAYLOAD_POINTER_KEY);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_gallery_detail;
    }

    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {
        mBinding = FragmentGalleryDetailBinding.bind(view);
        HashMap<Long,GalleryItem> galleryItemMap = (HashMap<Long,GalleryItem>) getActivity().getIntent().getExtras().get(GalleryViewModel.PAYLOAD_MAP_KEY);
        try {
            mGalleryPointer = getArguments().getLong(GalleryViewModel.PAYLOAD_POINTER_KEY);
        }catch (Throwable th){
            Timber.e(th,"[-]%s",getArguments().getLong(GalleryViewModel.PAYLOAD_POINTER_KEY));
            throw th;
        }
        mViewModel = new GalleryViewModel(galleryItemMap,mGalleryPointer);
        mBinding.setGalleryViewModel(mViewModel);
    }

    @Override
    public void reloadFragmentView() {
        requestContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Picasso.with(getContext()).cancelRequest(mImagePreview);
        Timber.d("View destroyed.");
    }
}
