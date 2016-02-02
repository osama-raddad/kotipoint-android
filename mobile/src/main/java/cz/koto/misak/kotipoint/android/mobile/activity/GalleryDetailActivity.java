package cz.koto.misak.kotipoint.android.mobile.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.util.ViewUtils;

public class GalleryDetailActivity extends AppCompatActivity {

    public static final String PAYLOAD_KEY = "koTiGalleryDetailPayload";

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

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
        ButterKnife.bind(this);
        setupActionBar();
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
}