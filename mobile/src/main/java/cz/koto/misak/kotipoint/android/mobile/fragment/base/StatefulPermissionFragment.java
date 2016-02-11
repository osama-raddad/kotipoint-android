package cz.koto.misak.kotipoint.android.mobile.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.util.NetworkUtils;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import rx.Observable;
import rx.Observer;
import timber.log.Timber;

public abstract class StatefulPermissionFragment extends PermissionFragment{

    private StatefulLayout mStatefulLayout;

    protected StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup stateful layout
        setupStatefulLayout(savedInstanceState, getFragmentView());

        if (savedInstanceState == null) {
            //Prevent unnecessary reloading!
            requestContent(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Observable<String> myObservable = Observable.just("");
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                reloadFragmentView();
            }
        };

        getFragmentView().setupObservables(myObservable,myObserver);
    }

    /**
     * Do request for content.
     * Permission request is called only when savedInstanceState is null.
     *
     * @param savedInstanceState
     */
    protected final void requestContent(Bundle savedInstanceState) {

        if (NetworkUtils.isOnline(getActivity())) {
            showProgress();
        /*
         * Request all permissions defined in getMandatoryPermissionList and
         * call doWithMandatoryPermissions() after all of them are granted.
         */
            if (savedInstanceState == null) {
                requestMandatoryPermissions();
            } else {
                Timber.d("KoTiNode reloaded not necessary.");
            }
        } else {
            showOffline();
        }
    }

    /**
     * Do request for content.
     * Permission request is called regardless the savedInstanceState.
     */
    protected final void requestContent() {
        requestContent(null);
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
        mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(View v, StatefulLayout.State state) {
                Timber.d("***StateChange:%s", state);

                if (state == StatefulLayout.State.NOPERMISSION) {
                    Timber.i("***StateChange>>:%s", state);
                }
            }
        });

        // restore state
        mStatefulLayout.restoreInstanceState(savedInstanceState);
    }

    protected final void showOffline() {
        mStatefulLayout.showOffline();
    }

    protected final void showEmpty() {
        mStatefulLayout.showEmpty();
    }

    protected final void showContent() {
        mStatefulLayout.showContent();
    }

    protected final boolean isContentLayoutVisible() {
        return (mStatefulLayout.getState() == StatefulLayout.State.CONTENT);
    }

    protected final boolean isProgressLayoutVisible() {
        return (mStatefulLayout.getState() == StatefulLayout.State.PROGRESS);
    }

    protected final void showProgress() {
        mStatefulLayout.showProgress();
    }

    protected final void showNoPermission() {
        mStatefulLayout.showNoPermission();
    }

    void saveLayoutState(Bundle outState) {
        // stateful layout state
        if (mStatefulLayout != null) mStatefulLayout.saveInstanceState(outState);
    }

    @Override
    protected void mandatoryPermissionNotGranted() {
        showNoPermission();
    }

    @Override
    protected List<AppPermissionEnum> getMandatoryPermissionList() {
        List<AppPermissionEnum> ret = new ArrayList<>();
        ret.add(AppPermissionEnum.ACCESS_NETWORK_STATE);
        return ret;
    }


    public abstract void reloadFragmentView();
}
