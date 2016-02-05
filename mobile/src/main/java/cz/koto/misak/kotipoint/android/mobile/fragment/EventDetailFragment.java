package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.activity.EventDetailActivity;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentEventDetailBinding;
import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.fragment.base.StatefulPermissionFragment;
import cz.koto.misak.kotipoint.android.mobile.viewModel.EventViewModel;

public class EventDetailFragment extends StatefulPermissionFragment {

    private FragmentEventDetailBinding mBinding;
    private EventViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentView = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // action bar menu
        super.onCreateOptionsMenu(menu, inflater);

        // TODO
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar menu behavior
        return super.onOptionsItemSelected(item);

        // TODO
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
        return R.layout.fragment_event_detail;
    }

    @Override
    protected void initOnCreateView(View view, Bundle savedInstanceState) {
        mBinding = FragmentEventDetailBinding.bind(view);
        mViewModel = new EventViewModel(getActivity().getIntent().getExtras().getParcelable(EventDetailActivity.PAYLOAD_KEY), view.getResources());
        mBinding.setViewModel(mViewModel);
    }


    public void doWithMandatoryPermissions() {
        showContent();
    }

    @Override
    public List<AppPermissionEnum> getMandatoryPermissionList() {
        return null;
    }
}
