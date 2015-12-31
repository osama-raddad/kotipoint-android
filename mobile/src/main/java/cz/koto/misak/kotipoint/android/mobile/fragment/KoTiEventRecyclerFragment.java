package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.adapter.KoTiEventRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;
import cz.koto.misak.kotipoint.android.mobile.view.LinearDividerItemDecoration;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;


public class KoTiEventRecyclerFragment extends StatefulPermissionFragment implements KoTiEventRecyclerViewAdapter.EventViewHolder.OnItemClickListener {
    private static final String ARGUMENT_EXAMPLE = "example";

    private View mFragmentView;

    private final static int LIMIT = 35;

    @Bind(R.id.RecyclerView)
    AutoLoadingRecyclerView<KoTiEvent> mRecyclerView;

    private KoTiEventRecyclerViewAdapter mRecyclerViewAdapter;


    public static KoTiEventRecyclerFragment newInstance(String example) {
        KoTiEventRecyclerFragment fragment = new KoTiEventRecyclerFragment();

        // arguments
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_EXAMPLE, example);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_autoloading_recycler, container, false);
        ButterKnife.bind(this, mFragmentView);
        setRetainInstance(true);
        init(mFragmentView, savedInstanceState);
        return mFragmentView;
    }

    private void init(View view, Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null) || (mRecyclerViewAdapter == null)) {
            if (mRecyclerViewAdapter == null) {
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            mRecyclerViewAdapter = new KoTiEventRecyclerViewAdapter(this);
            mRecyclerViewAdapter.setHasStableIds(true);
        }

        mRecyclerView.setSaveEnabled(true);

        // add decoration
        RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.setLimit(LIMIT);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLoadingObservable(offsetAndLimit -> KoTiNodeClient.getKoTiNodeClient(getContext()).eventList(offsetAndLimit.getOffset(), offsetAndLimit.getLimit()));
        mRecyclerView.setStatefulLayout(getFragmentView());
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

    private void handleArguments(Bundle arguments) {
        if (arguments.containsKey(ARGUMENT_EXAMPLE)) {
            String mExample = (String) arguments.get(ARGUMENT_EXAMPLE);
            Logcat.d("Handled %s value: %s", ARGUMENT_EXAMPLE, mExample);
        }
    }

    private void doLoading() {
        mRecyclerView.startLoading();
    }

    @Override
    public void onItemClick(View view, int position, long id, int viewType) {
        Intent i = new Intent(getContext(), EventDetailActivity.class);
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
