package cz.koto.misak.kotipoint.android.mobile.view.autoloading;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.KoTiPointBaseConfig;
import cz.koto.misak.kotipoint.android.mobile.adapter.AutoLoadingRecyclerViewAdapter;
import cz.koto.misak.kotipoint.android.mobile.entity.autoloading.AutoLoadingRecyclerViewException;
import cz.koto.misak.kotipoint.android.mobile.entity.autoloading.ILoading;
import cz.koto.misak.kotipoint.android.mobile.entity.autoloading.OffsetAndLimit;
import cz.koto.misak.kotipoint.android.mobile.util.BackgroundExecutor;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;


public class AutoLoadingRecyclerView<T,U extends RecyclerView.ViewHolder> extends RecyclerView {

    private static final int START_OFFSET = 0;
    private StatefulLayout mStatefulLayout;

    protected PublishSubject<OffsetAndLimit> scrollLoadingChannel = PublishSubject.create();
    protected Subscription loadNewItemsSubscription;
    protected Subscription subscribeToLoadingChannelSubscription;
    protected int limit;
    protected ILoading<T> iLoading;
    protected AutoLoadingRecyclerViewAdapter<T,U> autoLoadingRecyclerViewAdapter;
    // for restore after reorientation
    protected boolean firstPortionLoaded;
    protected boolean allPortionsLoaded;

    public AutoLoadingRecyclerView(Context context) {
        super(context);
        init();
    }

    public AutoLoadingRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoLoadingRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * required method
     * call after init all parameters in AutoLoadedRecyclerView
     */
    public void startLoading() {
        // if all data was loaded then new download is not needed
        if (allPortionsLoaded) {
            return;
        }
        // if first portion was loaded then subscribe to LoadingChannel
        if (firstPortionLoaded) {
            subscribeToLoadingChannel();
        } else {
            OffsetAndLimit offsetAndLimit = new OffsetAndLimit(START_OFFSET, getLimit());
            loadNewItems(offsetAndLimit);
        }
    }

    protected void init() {
        startScrollingChannel();
    }

    protected void startScrollingChannel() {
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = getLastVisibleItemPosition();
                int limit = getLimit();
                int updatePosition = getAdapter().getItemCount() - 1 - (limit / 2);
                if (position >= updatePosition) {
                    int offset = getAdapter().getItemCount();
                    OffsetAndLimit offsetAndLimit = new OffsetAndLimit(offset, limit);
                    scrollLoadingChannel.onNext(offsetAndLimit);
                }
            }
        });
    }

    protected int getLastVisibleItemPosition() {
        Class recyclerViewLMClass = getLayoutManager().getClass();
        if (recyclerViewLMClass == LinearLayoutManager.class || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
            return linearLayoutManager.findLastVisibleItemPosition();
        } else if (recyclerViewLMClass == StaggeredGridLayoutManager.class || StaggeredGridLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            List<Integer> intoList = new ArrayList<>();
                for (int i : into) {
                intoList.add(i);
            }
            return Collections.max(intoList);
        }
        throw new AutoLoadingRecyclerViewException("Unknown LayoutManager class: " + recyclerViewLMClass.toString());
    }

    public int getLimit() {
        if (limit <= 0) {
            throw new AutoLoadingRecyclerViewException("limit must be initialised! And limit must be more than zero!");
        }
        return limit;
    }

    /**
     * required method
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }


    /**
     * Unsafe (non-typed) setter.
     * @deprecated - use typed setter (below) instead!
     * @param adapter
     */
    @Deprecated
    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof AutoLoadingRecyclerViewAdapter) {
            super.setAdapter(adapter);
        } else {
            throw new AutoLoadingRecyclerViewException("Adapter must be implement IAutoLoadedAdapter");
        }
    }

    /**
     * required method
     */
    public void setAdapter(AutoLoadingRecyclerViewAdapter<T,U> autoLoadingRecyclerViewAdapter) {
        this.autoLoadingRecyclerViewAdapter = autoLoadingRecyclerViewAdapter;
        super.setAdapter(autoLoadingRecyclerViewAdapter);
    }

    public AutoLoadingRecyclerViewAdapter<T,U> getAdapter() {
        if (autoLoadingRecyclerViewAdapter == null) {
            throw new AutoLoadingRecyclerViewException("Null adapter. Please initialise adapter! "+iLoading);
        }
        return autoLoadingRecyclerViewAdapter;
    }

    public void setLoadingObservable(ILoading<T> iLoading) {
        this.iLoading = iLoading;
    }

    public ILoading<T> getLoadingObservable() {
        if (iLoading == null) {
            throw new AutoLoadingRecyclerViewException("Null LoadingObservable. Please initialise LoadingObservable!");
        }
        return iLoading;
    }

    protected void subscribeToLoadingChannel() {
        Subscriber<OffsetAndLimit> toLoadingChannelSubscriber = new Subscriber<OffsetAndLimit>() {
            @Override
            public void onCompleted() {
                //Do NOT use getter, null value in this case is expected.
                setLayoutContent(autoLoadingRecyclerViewAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("%s SubscribeToLoadingChannel error: %s", autoLoadingRecyclerViewAdapter,e);
                setLayoutOffline();
            }

            @Override
            public void onNext(OffsetAndLimit offsetAndLimit) {
                unsubscribe();
                loadNewItems(offsetAndLimit);
            }
        };
        subscribeToLoadingChannelSubscription = scrollLoadingChannel
                .subscribe(toLoadingChannelSubscriber);
    }

    protected void loadNewItems(OffsetAndLimit offsetAndLimit) {
        Subscriber<List<T>> loadNewItemsSubscriber = new Subscriber<List<T>>() {
            @Override
            public void onCompleted() {
                //Do NOT use getter, null value in this case is expected.
                setLayoutContent(autoLoadingRecyclerViewAdapter);
                getAdapter().hideAutoLoader();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("%s LoadNewItems error: %s", autoLoadingRecyclerViewAdapter,e);
                setLayoutOffline();
                subscribeToLoadingChannel();
            }

            @Override
            public void onNext(List<T> ts) {
                firstPortionLoaded = true;
                getAdapter().addNewItems(ts);
                getAdapter().notifyItemInserted(getAdapter().getItemCount() - ts.size());
                if (ts.size() > 0) {
                    subscribeToLoadingChannel();
                } else {
                    allPortionsLoaded = true;
                }
            }
        };

        if (firstPortionLoaded) {
            //add loader at the end
            getAdapter().showLoader();
        } else {
            setLayoutProgress();
        }

        loadNewItemsSubscription = getLoadingObservable().getLoadingObservable(offsetAndLimit)
                .subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadNewItemsSubscriber);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        setAdapter(null);
        scrollLoadingChannel.onCompleted();
        if (subscribeToLoadingChannelSubscription != null && !subscribeToLoadingChannelSubscription.isUnsubscribed()) {
            subscribeToLoadingChannelSubscription.unsubscribe();
        }
        if (loadNewItemsSubscription != null && !loadNewItemsSubscription.isUnsubscribed()) {
            try {
                loadNewItemsSubscription.unsubscribe();
            }catch (Throwable th){
                if (KoTiPointBaseConfig.DEV_API) {
                    Timber.e(th, "https://github.com/kaushikgopal/RxJava-Android-Samples/pull/26");
                }
            }
        }
        super.onDetachedFromWindow();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.firstPortionLoadedSaved = firstPortionLoaded;
        ss.allPortionsLoadedSaved = allPortionsLoaded;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state.getClass() == SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        firstPortionLoaded = ss.firstPortionLoadedSaved;
        allPortionsLoaded = ss.allPortionsLoadedSaved;
    }

    public static class SavedState extends BaseSavedState {
        boolean firstPortionLoadedSaved;
        boolean allPortionsLoadedSaved;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            boolean params[] = new boolean[]{firstPortionLoadedSaved, allPortionsLoadedSaved};
            out.writeBooleanArray(params);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel in) {
            super(in);
            boolean params[] = new boolean[2];
            in.readBooleanArray(params);
            firstPortionLoadedSaved = params[0];
            allPortionsLoadedSaved = params[1];
        }
    }

    public void setStatefulLayout(StatefulLayout statefulLayout) {
        mStatefulLayout = statefulLayout;
    }

    private void setLayoutContent() {
        if (mStatefulLayout != null) {
            mStatefulLayout.showContent();
        }
    }

    private void setLayoutContent(AutoLoadingRecyclerViewAdapter<T,U> adapter) {

        if ((adapter==null)||(adapter.getItemCount()<= 1)) {
            setLayoutOffline();//setLayoutEmpty();
        } else
            setLayoutContent();
    }

    private void setLayoutProgress() {
        if (mStatefulLayout != null) {
            mStatefulLayout.showProgress();
        }
    }

    private void setLayoutOffline() {
        if (mStatefulLayout != null) {
            mStatefulLayout.showOffline();
        }
    }

    private void setLayoutEmpty() {
        if (mStatefulLayout != null) {
            mStatefulLayout.showEmpty();
        }
    }

}
