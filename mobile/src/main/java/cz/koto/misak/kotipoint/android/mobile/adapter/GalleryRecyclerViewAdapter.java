package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentGalleryItemBinding;
import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.viewModel.GalleryViewModel;
import timber.log.Timber;

/**
 * http://blog.sqisland.com/2014/12/recyclerview-grid-with-header.html
 * <p>
 * http://www.slideshare.net/devunwired/mastering-recyclerview-layouts
 */
public class GalleryRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<GalleryItem, GalleryRecyclerViewAdapter.GalleryBindingHolder> {

    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    /*Temporary solution to distinguish helper (loader) item*/
    private static final long UNIQUE_LOADER_ID = -2L;

    private Context mContext;
    GalleryBindingHolder mFooterViewHolder = null;


    public GalleryRecyclerViewAdapter(Context context) {
        mContext = context;
       // addFooter(new GalleryItem("-", UNIQUE_LOADER_ID));

    }


    @Override
    public long getItemId(int position) {
        Timber.w("Items count %s, Requester position:%s , Item:%s", getItemCount(), position, getItem(position));
        if (getItem(position).getUrl() == null) {
            Timber.w("Null ID for position: %s", getItem(position));
        }
        return getItem(position).getId();
    }

    @Override
    public GalleryBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            /*Use binding.*/
            FragmentGalleryItemBinding fragmentEventItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.fragment_gallery_item,
                    parent,
                    false);
            return new GalleryBindingHolder(fragmentEventItemBinding);

        } else if (viewType == VIEW_TYPE_FOOTER) {
             /*Use plain inflate for loader.*/
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_footer, parent, false);
            return mFooterViewHolder = new GalleryBindingHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getId() == UNIQUE_LOADER_ID) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_IMAGE;
        }
    }

    @Override
    public void onBindViewHolder(GalleryBindingHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_IMAGE:
                FragmentGalleryItemBinding fragmentEventItemBinding = (FragmentGalleryItemBinding)holder.binding;
                fragmentEventItemBinding.setGalleryViewModel(new GalleryViewModel(getItem(position)));
                break;
            case VIEW_TYPE_FOOTER:
                break;
        }
    }

    public void hideAutoLoader() {
        if (mFooterViewHolder == null) return;
        mFooterViewHolder.hideProgress();
    }

    public void showLoader() {
        if (mFooterViewHolder == null) return;
        mFooterViewHolder.showProgress();
    }

    public static final class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public GalleryImageViewHolder(View itemView) {
            super(itemView);
            // find views
            mImageView = (ImageView) itemView.findViewById(R.id.fragment_gallery_item_image_view);
        }
    }


    /**
     * EventBindingHolder item.
     * <p>
     * TODO temporary (two in one) quick fix.
     * Currently could be initialized as binding item, or as helper loader holder.
     * This should be solved better (clearly) in the future.
     */
    public static class GalleryBindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        private ProgressBar mProgressBar;

        public GalleryBindingHolder(FragmentGalleryItemBinding binding) {
            super(binding.fragmentGalleryItem);
            this.binding = binding;
        }

        public GalleryBindingHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.fragment_gallery_footer_progress);
            hideProgress();
        }

        public void showProgress() {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        public void hideProgress() {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }
        }

        public boolean isLoader() {
            return (mProgressBar == null);
        }

    }

}