package cz.koto.misak.kotipoint.android.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;


public abstract class BaseFragment extends Fragment {

    View mFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mFragmentView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, mFragmentView);
        setRetainInstance(true);//TODO think about this, is it smart?
        initView(mFragmentView, savedInstanceState);
        return mFragmentView;
    }

    abstract @LayoutRes int getLayoutResource();
    abstract void initView(View view, Bundle savedInstanceState);
}
