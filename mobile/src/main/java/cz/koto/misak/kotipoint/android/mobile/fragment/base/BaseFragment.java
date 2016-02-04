package cz.koto.misak.kotipoint.android.mobile.fragment.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

    protected View mFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mFragmentView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, mFragmentView);
        setRetainInstance(true);
        initOnCreateView(mFragmentView, savedInstanceState);
        return mFragmentView;
    }

    /**
     * Attention! onSaveInstanceState is NOT called in following case:
     * When fragment is back from backstack (in this case, Fragment A),
     * the view inside Fragment A will be recreated following the Fragment Lifecycle documented here:
     * http://developer.android.com/guide/components/fragments.html
     *
     * TODO: One solution is here, but the behavior is strange at first try
     * http://inthecheesefactory.com/blog/best-approach-to-keep-android-fragment-state/en
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
        onSaveState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreState(savedInstanceState);
        }
    }
    protected abstract @LayoutRes int getLayoutResource();

    protected abstract void initOnCreateView(View view, Bundle savedInstanceState);

    /**
     * Restore your objects and eventually call renderView here.
     * @param savedInstanceState
     */
    protected abstract void onRestoreState(Bundle savedInstanceState);

    /**
     * Save your objects here.
     * @param outState
     */
    protected abstract void onSaveState(Bundle outState);

}
