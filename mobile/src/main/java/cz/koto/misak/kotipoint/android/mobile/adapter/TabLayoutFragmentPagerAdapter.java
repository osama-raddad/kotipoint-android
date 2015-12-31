package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cz.koto.misak.kotipoint.android.mobile.fragment.EventListFragment;


public class TabLayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final int FRAGMENT_COUNT = 1;


    public TabLayoutFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EventListFragment.newInstance("EVENT");
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "EVENT";
            default:
                return null;
        }
    }


    public void refill() {
        notifyDataSetChanged();
    }
}
