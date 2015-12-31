package cz.koto.misak.kotipoint.android.mobile.fragment;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;
import cz.koto.misak.kotipoint.android.mobile.utils.NetworkUtils;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;

public abstract class StatefulPermissionFragment extends PermissionFragment{


    private StatefulLayout mStatefulLayout;

    abstract StatefulLayout getFragmentView();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup stateful layout
        setupStatefulLayout(savedInstanceState, getFragmentView());

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
            showOffline();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveLayoutState(outState);
    }


    void setupStatefulLayout(Bundle savedInstanceState, StatefulLayout fragmentView) {
        // reference
        mStatefulLayout = fragmentView;

        // state change listener
        mStatefulLayout.setOnStateChangeListener((v, state) -> {
            Logcat.d("***StateChange:%s", state);

            if (state == StatefulLayout.State.CONTENT) {

            }
        });

//        // state change listener
//        mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener() {
//            @Override
//            public void onStateChange(View v, StatefulLayout.State state) {
//                Logcat.d("***StateChange:%s", state);
//
//                if (state == StatefulLayout.State.CONTENT) {
//
//                }
//            }
//        });

        // restore state
        mStatefulLayout.restoreInstanceState(savedInstanceState);
    }

    void showOffline(){
        mStatefulLayout.showOffline();
    }

    void showNoPermission(){
        mStatefulLayout.showNoPermission();
    }

    void saveLayoutState(Bundle outState) {
        // stateful layout state
        if(mStatefulLayout!=null) mStatefulLayout.saveInstanceState(outState);
    }

    @Override
    public void permissionNotGranted() {
        showNoPermission();
    }

    @Override
    public List<AppPermissionEnum> getPermissionList() {
        List<AppPermissionEnum> ret = new ArrayList<>();
        ret.add(AppPermissionEnum.NETWORK_STATE);
        return ret;
    }
}
