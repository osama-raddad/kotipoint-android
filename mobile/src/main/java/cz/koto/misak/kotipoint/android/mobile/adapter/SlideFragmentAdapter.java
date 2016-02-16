package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import cz.koto.misak.kotipoint.android.mobile.fragment.GaleryDetailFragment;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.viewModel.GalleryViewModel;
import timber.log.Timber;


public class SlideFragmentAdapter extends FragmentPagerAdapter {
  private final Map<Long,GalleryItem> data;

  private final Map<Long, Fragment> fragments;

  public SlideFragmentAdapter(Context context, FragmentManager fm, Map<Long,GalleryItem> galleryItemMap) {
    super(fm);
    this.data = galleryItemMap;
    this.fragments = new HashMap<>();

    for (GalleryItem image : data.values()) {
      GaleryDetailFragment f =   GaleryDetailFragment.newInstance(context, image.getId());
      Bundle b = new Bundle();
      b.putLong(GalleryViewModel.PAYLOAD_POINTER_KEY, image.getId());
      f.setArguments(b);
      fragments.put(image.getId(), f);
    }
  }

  public GalleryItem getImage(int position) {
    return data.get(position);
  }

//  public GaleryDetailFragment fragmentForPosition(int position) {
//    return (GaleryDetailFragment) fragments.get(getImage(position));
//  }

  @Override
  public Fragment getItem(int position) {
    Fragment ret =  fragments.get(Long.valueOf(position));
      if (ret==null){
          Timber.e("Missing fragment on position: %s!",position);
          new RuntimeException("");
      }
      return ret;
  }

  @Override
  public int getCount() {
    return data.size();
  }
}
