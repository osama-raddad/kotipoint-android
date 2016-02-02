package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentEventItemBinding;
import cz.koto.misak.kotipoint.android.mobile.model.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.util.Logcat;
import cz.koto.misak.kotipoint.android.mobile.viewModel.EventViewModel;

public class EventRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<KoTiEvent, EventRecyclerViewAdapter.EventBindingHolder> {

    private static final int VIEW_TYPE_EVENT = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    /*Temporary solution to distinguish helper (loader) item*/
    private static final int UNIQUE_LOADER_ID = -2;

    private Context mContext;
    private EventBindingHolder mFooterViewHolder;

    public EventRecyclerViewAdapter(Context context) {
        mContext = context;
        addFooter(new KoTiEvent(UNIQUE_LOADER_ID,"Loader"));
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
    public EventBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EVENT) {
            /*Use binding.*/
            FragmentEventItemBinding fragmentEventItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.fragment_event_item,
                    parent,
                    false);
            return new EventBindingHolder(fragmentEventItemBinding);

        } else if (viewType == VIEW_TYPE_FOOTER) {
            /*Use plain inflate for loader.*/
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_footer, parent, false);
            return mFooterViewHolder =  new EventBindingHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getmId()==UNIQUE_LOADER_ID){
            return VIEW_TYPE_FOOTER;
        }else{
            return VIEW_TYPE_EVENT;
        }
    }

    @Override
    public void onBindViewHolder(EventBindingHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_EVENT:
                FragmentEventItemBinding fragmentEventItemBinding = (FragmentEventItemBinding)holder.binding;
                fragmentEventItemBinding.setViewModel(new EventViewModel(mContext,getItem(position)));
                break;
            case VIEW_TYPE_FOOTER:
                break;
        }
    }


    public void hideAutoLoader(){
        if (mFooterViewHolder==null)return;
        mFooterViewHolder.hideProgress();
    }

    public  void showLoader(){
      if (mFooterViewHolder==null)return;
        mFooterViewHolder.showProgress();
    }

    /**
     * EventBindingHolder item.
     *
     * TODO temporary (two in one) quick fix.
     * Currently could be initialized as binding item, or as helper loader holder.
     * This should be solved better (clearly) in the future.
     */
    public static class EventBindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        private ProgressBar mProgressBar;

        public EventBindingHolder(FragmentEventItemBinding binding) {
            super(binding.eventCardView);
            this.binding = binding;
        }

        public EventBindingHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.fragment_event_footer_progress);
            hideProgress();
        }

        public void showProgress(){
            if (mProgressBar!=null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        public void hideProgress(){
            if (mProgressBar!=null) {
                mProgressBar.setVisibility(View.GONE);
            }
        }

        public boolean isLoader(){
            return  (mProgressBar==null);
        }

    }

}