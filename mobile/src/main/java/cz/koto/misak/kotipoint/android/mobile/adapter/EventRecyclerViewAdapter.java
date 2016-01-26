package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentEventItemBinding;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import cz.koto.misak.kotipoint.android.mobile.util.Logcat;
import cz.koto.misak.kotipoint.android.mobile.viewModel.EventViewModel;

public class EventRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<KoTiEvent, EventRecyclerViewAdapter.EventBindingHolder> {

    private static final int VIEW_TYPE_EVENT = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    private Context mContext;

    public EventRecyclerViewAdapter(Context context) {
        mContext = context;
        //addFooter(new KoTiEvent(-2,"Loader"));
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
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_item, parent, false);
//            return new EventViewHolder(v, mListener);
            FragmentEventItemBinding fragmentEventItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.fragment_event_item,
                    parent,
                    false);
            return new EventBindingHolder(fragmentEventItemBinding);

        } else if (viewType == VIEW_TYPE_FOOTER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_footer, parent, false);
//            return mFooterViewHolder =  new FooterViewHolder(view);
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
    public void onBindViewHolder(EventBindingHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_EVENT:
//                onBindTextHolder(holder, position);
                FragmentEventItemBinding fragmentEventItemBinding = (FragmentEventItemBinding)holder.binding;
                fragmentEventItemBinding.setViewModel(new EventViewModel(mContext,getItem(position)));
                break;
            case VIEW_TYPE_FOOTER:
                break;
        }
    }

//    private void onBindTextHolder(EventBindingHolder holder, int position) {
//        EventBindingHolder mainHolder = (EventBindingHolder) holder;
//        mainHolder.mNameTextView.setText(getItem(position).getmHeadline());
//    }


    public void hideAutoLoader(){
//        if (mFooterViewHolder==null)return;
//        mFooterViewHolder.hideProgress();
    }

    public  void showLoader(){
//      if (mFooterViewHolder==null)return;
//        mFooterViewHolder.showProgress();
    }

//    public static final class EventViewHolder extends RecyclerView.ViewHolder {
//        private TextView mNameTextView;
//
//        public EventViewHolder(View itemView) {
//            super(itemView);
//
//            // find views
//            mNameTextView = (TextView) itemView.findViewById(R.id.fragment_event_item_name);
//        }
//
//
//        public void bindData(KoTiEvent koTiEvent) {
//            mNameTextView.setText(koTiEvent.getmHeadline());
//        }
//    }
//
//    public static final class FooterViewHolder extends RecyclerView.ViewHolder {
//
//        private ProgressBar mProgressBar;
//
//        public FooterViewHolder(View itemView) {
//            super(itemView);
//
//            mProgressBar = (ProgressBar) itemView.findViewById(R.id.fragment_event_footer_progress);
//            hideProgress();
//        }
//
//        public void showProgress(){
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//
//        public void hideProgress(){
//            mProgressBar.setVisibility(View.GONE);
//        }
//    }

    public static class EventBindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public EventBindingHolder(FragmentEventItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

//        public BindingHolder(FragmentEventFooter binding) {
//            super(binding.containerItem);
//            this.binding = binding;
//        }
    }

}