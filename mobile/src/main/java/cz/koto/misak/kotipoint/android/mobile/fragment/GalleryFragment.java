package cz.koto.misak.kotipoint.android.mobile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.GalleryDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.adapter.GalleryRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;
import cz.koto.misak.kotipoint.android.mobile.view.LinearDividerItemDecoration;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;

public class GalleryFragment extends StatefulPermissionFragment implements GalleryRecyclerViewAdapter.GalleryViewHolder.OnItemClickListener{

    private final static int LIMIT = 35;

    @Bind(R.id.RecyclerView)
    AutoLoadingRecyclerView<GalleryItem> mRecyclerView;

    private GalleryRecyclerViewAdapter mRecyclerViewAdapter;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_gallery;
    }

    @Override
    void initView(View view, Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null) || (mRecyclerViewAdapter == null)) {
            if (mRecyclerViewAdapter == null) {
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            mRecyclerViewAdapter = new GalleryRecyclerViewAdapter(this, getContext());
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
    StatefulLayout getFragmentView() {
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

    @Override
    public void onItemClick(View view, int position, long id, int viewType) {
        Intent i = GalleryDetailActivity.newIntent(getContext());
        i.putExtra(GalleryDetailActivity.PAYLOAD_KEY, mRecyclerViewAdapter.getItem(position));
        getContext().startActivity(i);
    }

    @Override
    public void onItemLongClick(View view, int position, long id, int viewType) {
        GalleryItem galleryItem = mRecyclerViewAdapter.getItem(position);
        if (galleryItem == null) return;
        Toast.makeText(getContext(), String.valueOf(galleryItem.getId()), Toast.LENGTH_SHORT).show();
    }

    private void doLoading() {
        mRecyclerView.startLoading();
    }
}
