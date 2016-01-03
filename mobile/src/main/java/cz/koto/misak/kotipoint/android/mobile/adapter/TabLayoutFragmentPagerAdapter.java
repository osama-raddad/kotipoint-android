package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cz.koto.misak.kotipoint.android.mobile.fragment.EventListFragment;
import cz.koto.misak.kotipoint.android.mobile.fragment.GalleryFragment;


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
            case 1:
                return EventListFragment.newInstance("EVENT");
            case 0:
                return GalleryFragment.newInstance();
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "EVENT";
            case 0:
                return "GALLERY";
            default:
                return null;
        }
    }


    public void refill() {
        notifyDataSetChanged();
    }
}
