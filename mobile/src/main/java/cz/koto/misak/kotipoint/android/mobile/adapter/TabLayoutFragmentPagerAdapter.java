package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.fragment.EventListFragment;
import cz.koto.misak.kotipoint.android.mobile.fragment.GalleryFragment;

/**
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 */
public class TabLayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final int FRAGMENT_COUNT = 2;

    private Context mContext;

    private int[] imageResId = {
            R.drawable.ic_event_note,
            R.drawable.ic_gallery
    };

    public TabLayoutFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
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
            case 1:
                return GalleryFragment.newInstance();
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        Drawable image = ContextCompat.getDrawable(mContext, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }


    public void refill() {
        notifyDataSetChanged();
    }
}
