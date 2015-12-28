package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.adapter.KoTiEventRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;
import cz.koto.misak.kotipoint.android.mobile.utils.NetworkUtils;
import cz.koto.misak.kotipoint.android.mobile.view.LinearDividerItemDecoration;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;


public class KoTiEventRecyclerFragment extends PermissionFragment implements KoTiEventRecyclerViewAdapter.EventViewHolder.OnItemClickListener{
    private static final String ARGUMENT_EXAMPLE = "example";

    private View mRootView;
    private StatefulLayout mStatefulLayout;

    private final static int LIMIT = 35;
    private AutoLoadingRecyclerView<KoTiEvent> recyclerView;
    private KoTiEventRecyclerViewAdapter recyclerViewAdapter;


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
        mRootView = inflater.inflate(R.layout.fragment_autoloading_recycler, container, false);
        setRetainInstance(true);
        init(mRootView, savedInstanceState);
        return mRootView;
    }

    private void init(View view, Bundle savedInstanceState) {
        recyclerView = (AutoLoadingRecyclerView) view.findViewById(R.id.RecyclerView);
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null)||(recyclerViewAdapter==null)) {
            if (recyclerViewAdapter==null){
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            recyclerViewAdapter = new KoTiEventRecyclerViewAdapter(this);
            recyclerViewAdapter.setHasStableIds(true);
        }

        recyclerView.setSaveEnabled(true);

        // add decoration
        RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setLimit(LIMIT);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLoadingObservable(offsetAndLimit -> KoTiNodeClient.getKoTiNodeClient(getContext()).eventList(offsetAndLimit.getOffset(), offsetAndLimit.getLimit()));
        recyclerView.setStatefulLayout((StatefulLayout) mRootView);
//        // start loading for the first time
//        if (savedInstanceState == null) {
//            if (mStatefulLayout.getState() == null) {
//                recyclerView.startLoading();
//            }
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup stateful layout
        setupStatefulLayout(savedInstanceState);

        if (NetworkUtils.isOnline(getActivity())) {
        /*
         * Request all permissions defined in getPermissionList and
         * call doWithPermissions() after all of them are granted.
         */
            if (savedInstanceState == null) {
                requestPermissions();
            }else{
                Logcat.d("KoTiNode reloaded not necessary.");
            }
        }else {
            mStatefulLayout.showOffline();;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        // save current instance state
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);

        // stateful layout state
        if(mStatefulLayout!=null) mStatefulLayout.saveInstanceState(outState);
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
        // load data
//        if (mStatefulLayout.getState() == null) {
            recyclerView.startLoading();
//        }
    }


    private void setupStatefulLayout(Bundle savedInstanceState) {
        // reference
        mStatefulLayout = (StatefulLayout) mRootView;

        // state change listener
        mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(View v, StatefulLayout.State state) {
                Logcat.d("***StateChange:%s", state);

                if (state == StatefulLayout.State.CONTENT) {

                }
            }
        });

        // restore state
        mStatefulLayout.restoreInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position, long id, int viewType) {
        Intent i = new Intent(getContext(),EventDetailActivity.class);
        i.putExtra(EventDetailActivity.PAYLOAD_KEY, recyclerViewAdapter.getItem(position));
        getContext().startActivity(i);
    }

    @Override
    public void onItemLongClick(View view, int position, long id, int viewType) {
        KoTiEvent koTiEvent = recyclerViewAdapter.getItem(position);
        if (koTiEvent==null) return;
        Toast.makeText(getContext(), koTiEvent.getmTextCapital() == null ? koTiEvent.getmHeadline() : koTiEvent.getmTextCapital(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doWithPermissions() {
        doLoading();
    }

    @Override
    public void permissionNotGranted() {
        mStatefulLayout.showOffline();
    }

    @Override
    public List<AppPermissionEnum> getPermissionList() {
        return Arrays.asList(AppPermissionEnum.NETWORK_STATE,AppPermissionEnum.INTERNET);
    }
}
