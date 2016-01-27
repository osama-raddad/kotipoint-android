package cz.koto.misak.kotipoint.android.mobile.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.adapter.GalleryRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.util.Logcat;
import cz.koto.misak.kotipoint.android.mobile.view.LinearDividerItemDecoration;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;

public class GalleryFragment extends StatefulPermissionFragment {

    private final static int LIMIT = 12;

    //public static final String M_TODAY_WEATHER_WRAPPER_0784150041885340 = "mTodayWeatherWrapper0784150041885340";

    @Bind(R.id.RecyclerView)
    AutoLoadingRecyclerView<GalleryItem, GalleryRecyclerViewAdapter.GalleryBindingHolder> mRecyclerView;

    private GalleryRecyclerViewAdapter mRecyclerViewAdapter;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
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
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null) || (mRecyclerViewAdapter == null)) {
            if (mRecyclerViewAdapter == null) {
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            mRecyclerViewAdapter = new GalleryRecyclerViewAdapter(getContext());
            mRecyclerViewAdapter.setHasStableIds(true);
        }

        mRecyclerView.setSaveEnabled(true);

        // add decoration
        RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.setLimit(LIMIT);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLoadingObservable(offsetAndLimit -> KoTiNodeClient.getKoTiNodeClient(getContext()).galleryList(offsetAndLimit.getOffset(), offsetAndLimit.getLimit()));
        mRecyclerView.setStatefulLayout(getFragmentView());
    }


    @Override
    protected StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void doWithPermissions() {
        doLoading();
    }

    @Override
    public List<AppPermissionEnum> getPermissionList() {
        List<AppPermissionEnum> ret = super.getPermissionList();
        ret.addAll(Arrays.asList(AppPermissionEnum.INTERNET));
        return ret;
    }

    private void doLoading() {
        mRecyclerView.startLoading();
    }
}
