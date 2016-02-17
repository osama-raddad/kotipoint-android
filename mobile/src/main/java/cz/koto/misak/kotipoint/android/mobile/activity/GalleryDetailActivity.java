package cz.koto.misak.kotipoint.android.mobile.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.adapter.SlideFragmentAdapter;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.util.ViewUtils;
import cz.koto.misak.kotipoint.android.mobile.viewModel.GalleryViewModel;

public class GalleryDetailActivity extends AppCompatActivity {

    private SlideFragmentAdapter fragmentAdapter;


    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.pager)
    public ViewPager pager;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GalleryDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
        ButterKnife.bind(this);
        setupActionBar();

        HashMap<Long,GalleryItem> galleryItemMap = (HashMap<Long,GalleryItem>) getIntent().getExtras().get(GalleryViewModel.PAYLOAD_MAP_KEY);
        Long galleryPointer = getIntent().getExtras().getLong(GalleryViewModel.PAYLOAD_POINTER_KEY);
        fragmentAdapter = new SlideFragmentAdapter(this, getSupportFragmentManager(), galleryItemMap);
        pager.setAdapter(fragmentAdapter);
        pager.setCurrentItem(galleryPointer.intValue());
        pager.setOffscreenPageLimit(getResources().getInteger(
                R.integer.gallery_pager_offscreen_limit));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        ViewUtils.setUpDetailActionBar(getSupportActionBar());
    }

//    @OnPageChange(value = R.id.pager, callback = OnPageChange.Callback.PAGE_SCROLLED)
//    void onPageSelected(int position, float positionOffset, int positionOffsetPixels) {
//        if (positionOffsetPixels == 0) {
//            return;
//        }
//
//        GalleryDetailSlideFragment leftFragment = fragmentAdapter.fragmentForPosition(position);
//        GalleryDetailSlideFragment rightFragment = fragmentAdapter.fragmentForPosition(position + 1);
//    }

}