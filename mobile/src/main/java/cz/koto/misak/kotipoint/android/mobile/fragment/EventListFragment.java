package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cz.koto.misak.kotipoint.android.mobile.KoTiPointBaseConfig;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.adapter.EventRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.fragment.base.StatefulPermissionFragment;
import cz.koto.misak.kotipoint.android.mobile.model.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.rest.KoTiNodeClient;
import cz.koto.misak.kotipoint.android.mobile.util.Logcat;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import cz.koto.misak.kotipoint.android.mobile.view.autoloading.AutoLoadingRecyclerView;


public class EventListFragment extends StatefulPermissionFragment {
    private static final String TITLE_KEY = "key_title";

    private final static int LIMIT = 35;

    @Bind(R.id.RecyclerView)
    AutoLoadingRecyclerView<KoTiEvent,EventRecyclerViewAdapter.EventBindingHolder> mRecyclerView;

    private EventRecyclerViewAdapter mRecyclerViewAdapter;


    public static EventListFragment newInstance(String title) {
        EventListFragment fragment = new EventListFragment();

        // arguments
        Bundle arguments = new Bundle();
        arguments.putString(TITLE_KEY, title);
        fragment.setArguments(arguments);

        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // handle fragment arguments
//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            handleArguments(arguments);
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if ((savedInstanceState == null) || (mRecyclerViewAdapter == null)) {
            if (mRecyclerViewAdapter == null) {
                Logcat.d("RecyclerViewAdapter is NULL, init it!");
            }
            mRecyclerViewAdapter = new EventRecyclerViewAdapter(getContext());
            mRecyclerViewAdapter.setHasStableIds(true);
        }

        mRecyclerView.setSaveEnabled(true);

        // add decoration
//        RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
//        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.setLimit(LIMIT);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLoadingObservable(offsetAndLimit -> KoTiNodeClient.getKoTiNodeClient(getContext()).eventList(offsetAndLimit.getOffset(), offsetAndLimit.getLimit(), KoTiPointBaseConfig.API_KOTINODE_TEST_DELAY));
        mRecyclerView.setStatefulLayout(getFragmentView());
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
    @LayoutRes
    protected int getLayoutResource() {
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
    protected StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }



    @Override
    public void doWithMandatoryPermissions() {
        doLoading();
    }


    @Override
    public List<AppPermissionEnum> getMandatoryPermissionList() {
        List<AppPermissionEnum> ret = super.getMandatoryPermissionList();
        ret.addAll(Arrays.asList(AppPermissionEnum.INTERNET,AppPermissionEnum.ACCESS_FINE_LOCATION));
        return ret;
    }
}
