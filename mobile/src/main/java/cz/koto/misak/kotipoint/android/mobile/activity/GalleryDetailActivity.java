package cz.koto.misak.kotipoint.android.mobile.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cz.koto.misak.kotipoint.android.mobile.R;

public class GalleryDetailActivity extends AppCompatActivity {

    public static final String PAYLOAD_KEY = "koTiGalleryDetailPayload";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GalleryDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
        setupActionBar();
    }


    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setHomeButtonEnabled(true);
    }
}