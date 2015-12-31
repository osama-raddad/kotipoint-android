package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.utils.Logcat;

public class EventRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<KoTiEvent> {

    private static final int VIEW_TYPE_EVENT = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    private EventViewHolder.OnItemClickListener mListener;
    FooterViewHolder mFooterViewHolder = null;


    public EventRecyclerViewAdapter(EventViewHolder.OnItemClickListener listener) {
        mListener = listener;
        addFooter(new KoTiEvent(-2,"Loader"));

    }


    @Override
    public long getItemId(int position) {
        Logcat.w("Items count %s, Requester position:%s , Item:%s",getItemCount(), position,getItem(position));
        if (getItem(position).getmId()==null) {
            Logcat.w("Null ID for position: %s",getItem(position));
        }
        return getItem(position).getmId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EVENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_item, parent, false);
            return new EventViewHolder(v, mListener);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_footer, parent, false);
            return mFooterViewHolder =  new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getmId()<-1){
            return VIEW_TYPE_FOOTER;
        }else{
            return VIEW_TYPE_EVENT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_EVENT:
                onBindTextHolder(holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                break;
        }
    }

    private void onBindTextHolder(RecyclerView.ViewHolder holder, int position) {
        EventViewHolder mainHolder = (EventViewHolder) holder;
        mainHolder.mNameTextView.setText(getItem(position).getmHeadline());
    }


    public void hideAutoLoader(){
        if (mFooterViewHolder==null)return;
        mFooterViewHolder.hideProgress();
    }

    public  void showLoader(){
      if (mFooterViewHolder==null)return;
        mFooterViewHolder.showProgress();
    }

    public static final class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mNameTextView;
        private OnItemClickListener mListener;


        public interface OnItemClickListener {
            void onItemClick(View view, int position, long id, int viewType);

            void onItemLongClick(View view, int position, long id, int viewType);
        }


        public EventViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;

            // set listener
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            // find views
            mNameTextView = (TextView) itemView.findViewById(R.id.fragment_recycler_item_name);
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


        public void bindData(KoTiEvent koTiEvent) {
            mNameTextView.setText(koTiEvent.getmHeadline());
        }
    }

    public static final class FooterViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.fragment_recycler_footer_progress);
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