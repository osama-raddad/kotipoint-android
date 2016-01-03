package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;

/**
 * http://blog.sqisland.com/2014/12/recyclerview-grid-with-header.html
 *
 * http://www.slideshare.net/devunwired/mastering-recyclerview-layouts
 */
public class GalleryRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<GalleryItem> {

    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    private GalleryViewHolder.OnItemClickListener mListener;
    FooterViewHolder mFooterViewHolder = null;


    public GalleryRecyclerViewAdapter(GalleryViewHolder.OnItemClickListener listener) {
        mListener = listener;
        addFooter(new GalleryItem("-",-2L));

    }


    @Override
    public long getItemId(int position) {
        Logcat.w("Items count %s, Requester position:%s , Item:%s",getItemCount(), position,getItem(position));
        if (getItem(position).getmUrl()==null) {
            Logcat.w("Null ID for position: %s",getItem(position));
        }
        return getItem(position).getmId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_item, parent, false);
            return new GalleryViewHolder(v, mListener);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_footer, parent, false);
            return mFooterViewHolder =  new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getmId()<-1){
            return VIEW_TYPE_FOOTER;
        }else{
            return VIEW_TYPE_IMAGE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_IMAGE:
                onBindTextHolder(holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                break;
        }
    }

    private void onBindTextHolder(RecyclerView.ViewHolder holder, int position) {
        GalleryViewHolder mainHolder = (GalleryViewHolder) holder;
        mainHolder.mNameTextView.setText(getItem(position).getmUrl());
    }


    public void hideAutoLoader(){
        if (mFooterViewHolder==null)return;
        mFooterViewHolder.hideProgress();
    }

    public  void showLoader(){
      if (mFooterViewHolder==null)return;
        mFooterViewHolder.showProgress();
    }

    public static final class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mNameTextView;
        private OnItemClickListener mListener;


        public interface OnItemClickListener {
            void onItemClick(View view, int position, long id, int viewType);

            void onItemLongClick(View view, int position, long id, int viewType);
        }


        public GalleryViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;

            // set listener
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            // find views
            mNameTextView = (TextView) itemView.findViewById(R.id.fragment_gallery_item_name);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(view, position, getItemId(), getItemViewType());
            }
        }


        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemLongClick(view, position, getItemId(), getItemViewType());
            }
            return true;
        }


        public void bindData(GalleryItem galleryItem) {
            mNameTextView.setText(galleryItem.getmUrl());
        }
    }

    public static final class FooterViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.fragment_gallery_footer_progress);
            hideProgress();
        }


        public void bindData(Object object) {
            // do nothing
        }

        public void showProgress(){
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public void hideProgress(){
            mProgressBar.setVisibility(View.GONE);
        }
    }

}