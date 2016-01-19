package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.KoTiPointConfig;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.adapter.EventRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;
import cz.koto.misak.kotipoint.android.mobile.view.LinearDividerItemDecoration;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;


public class EventListFragment extends StatefulPermissionFragment implements EventRecyclerViewAdapter.EventViewHolder.OnItemClickListener {
    private static final String TITLE_KEY = "key_title";

    private final static int LIMIT = 35;

    @Bind(R.id.RecyclerView)
    AutoLoadingRecyclerView<KoTiEvent> mRecyclerView;

    private EventRecyclerViewAdapter mRecyclerViewAdapter;


    public static EventListFragment newInstance(String title) {
        EventListFragment fragment = new EventListFragment();

        // arguments
        Bundle arguments = new Bundle();
        arguments.putString(TITLE_KEY, title);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // handle fragment arguments
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    void initView(View view, Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null) || (mRecyclerViewAdapter == null)) {
            if (mRecyclerViewAdapter == null) {
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            mRecyclerViewAdapter = new EventRecyclerViewAdapter(this);
            mRecyclerViewAdapter.setHasStableIds(true);
        }

        mRecyclerView.setSaveEnabled(true);

        // add decoration
        RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.setLimit(LIMIT);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLoadingObservable(offsetAndLimit -> KoTiNodeClient.getKoTiNodeClient(getContext()).eventList(offsetAndLimit.getOffset(), offsetAndLimit.getLimit(), KoTiPointConfig.API_KOTINODE_TEST_DELAY));
        mRecyclerView.setStatefulLayout(getFragmentView());
    }

    @Override
    @LayoutRes
    int getLayoutResource() {
        return R.layout.fragment_event_list;
    }

    private void handleArguments(Bundle arguments) {
        if (arguments.containsKey(TITLE_KEY)) {
            String mTitle = (String) arguments.get(TITLE_KEY);
            Logcat.d("Handled %s value: %s", TITLE_KEY, mTitle);
        }
    }

    private void doLoading() {
        mRecyclerView.startLoading();
    }

    @Override
    public void onItemClick(View view, int position, long id, int viewType) {
        Intent i = EventDetailActivity.newIntent(getContext());
        i.putExtra(EventDetailActivity.PAYLOAD_KEY, mRecyclerViewAdapter.getItem(position));
        getContext().startActivity(i);
    }

    @Override
    StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void onItemLongClick(View view, int position, long id, int viewType) {
        KoTiEvent koTiEvent = mRecyclerViewAdapter.getItem(position);
        if (koTiEvent == null) return;
        Toast.makeText(getContext(), koTiEvent.getmTextCapital() == null ? koTiEvent.getmHeadline() : koTiEvent.getmTextCapital(), Toast.LENGTH_SHORT).show();
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
}
