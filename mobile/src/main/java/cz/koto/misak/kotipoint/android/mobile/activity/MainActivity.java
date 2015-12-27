package cz.koto.misak.kotipoint.android.mobile.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.adapter.TabLayoutFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private TabLayoutFragmentPagerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindData();
    }


    private void bindData()
    {
        // reference
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_tablayout_pager);
        viewPager.setOffscreenPageLimit(TabLayoutFragmentPagerAdapter.FRAGMENT_COUNT-1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_tablayout_tabs);

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
