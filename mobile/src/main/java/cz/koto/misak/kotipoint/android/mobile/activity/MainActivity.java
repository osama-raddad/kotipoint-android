package cz.koto.misak.kotipoint.android.mobile.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.adapter.TabLayoutFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private TabLayoutFragmentPagerAdapter mAdapter;

    @Bind(R.id.activity_tablayout_pager)
    ViewPager viewPager;

    @Bind(R.id.activity_tablayout_tabs)
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindData();
    }


    private void bindData()
    {
        // reference
        viewPager.setOffscreenPageLimit(TabLayoutFragmentPagerAdapter.FRAGMENT_COUNT-1);

        // pager content
        if(mAdapter==null)
        {
            // create adapter
            mAdapter = new TabLayoutFragmentPagerAdapter(getSupportFragmentManager());
        }
        else
        {
            // refill adapter
            mAdapter.refill();
        }

        // set adapter
        viewPager.setAdapter(mAdapter);

        // tab layout
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//TabLayout.MODE_SCROLLABLE
    }
}
