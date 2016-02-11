package cz.koto.misak.kotipoint.android.mobile.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.FragmentPlaceholderOfflineBinding;
import cz.koto.misak.kotipoint.android.mobile.viewModel.StatefulLayoutModel;
import rx.Observer;
import timber.log.Timber;

// code inspired by: https://github.com/jakubkinst/Android-StatefulView
public class StatefulLayout extends FrameLayout {
    private static final String SAVED_STATE = "stateful_layout_state";

    private State mInitialState;
    private int mProgressLayoutId;
    private int mOfflineLayoutId;
    private int mEmptyLayoutId;
    private int mNoPermissionLayoutId;

    private View mContentLayout;
    private View mProgressLayout;
    private View mOfflineLayout;
    private View mEmptyLayout;
    private View mNoPermissionLayout;
    private State mState;
    private OnStateChangeListener mOnStateChangeListener;

    private FragmentPlaceholderOfflineBinding mPlaceholderOfflineBinding;
    private StatefulLayoutModel mStatefulLayoutModel;


    public enum State {
        CONTENT(0), PROGRESS(1), OFFLINE(2), EMPTY(3), NOPERMISSION(4);

        private final int mValue;


        public static State valueToState(int value) {
            State[] values = State.values();
            return values[value];
        }


        private State(int value) {
            mValue = value;
        }


        public int getValue() {
            return mValue;
        }
    }


    public interface OnStateChangeListener {
        void onStateChange(View v, State state);
    }


    public StatefulLayout(Context context) {
        this(context, null);
    }


    public StatefulLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout);
        if (typedArray.hasValue(R.styleable.StatefulLayout_state)) {
            int initialStateValue = typedArray.getInt(R.styleable.StatefulLayout_state, State.CONTENT.getValue());
            mInitialState = State.valueToState(initialStateValue);
        }
        if (typedArray.hasValue(R.styleable.StatefulLayout_progressLayout) &&
                typedArray.hasValue(R.styleable.StatefulLayout_offlineLayout) &&
                typedArray.hasValue(R.styleable.StatefulLayout_emptyLayout) &&
                typedArray.hasValue(R.styleable.StatefulLayout_noPermissionLayout)) {
            mProgressLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_progressLayout, 0);
            mOfflineLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_offlineLayout, 0);
            mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_emptyLayout, 0);
            mNoPermissionLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_noPermissionLayout, 0);
        } else {
            throw new IllegalArgumentException("Attributes progressLayout, offlineLayout, emptyLayout nad noPermissionLayout are mandatory");
        }
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupView();
    }


    public void showContent() {
        setState(State.CONTENT);
    }


    public void showProgress() {
        setState(State.PROGRESS);
    }


    public void showOffline() {
        setState(State.OFFLINE);
    }


    public void showEmpty() {
        setState(State.EMPTY);
    }

    public void showNoPermission() {
        setState(State.NOPERMISSION);
    }


    public State getState() {
        return mState;
    }


    public void setState(State state) {
        Timber.d(">>setState:%s", state);
        mState = state;
        mContentLayout.setVisibility(state == State.CONTENT ? VISIBLE : GONE);
        mProgressLayout.setVisibility(state == State.PROGRESS ? VISIBLE : GONE);
        mOfflineLayout.setVisibility(state == State.OFFLINE ? VISIBLE : GONE);
        mEmptyLayout.setVisibility(state == State.EMPTY ? VISIBLE : GONE);
        mNoPermissionLayout.setVisibility(state == State.NOPERMISSION ? VISIBLE : GONE);
        if (mOnStateChangeListener != null) mOnStateChangeListener.onStateChange(this, state);
    }


    public void setOnStateChangeListener(OnStateChangeListener l) {
        mOnStateChangeListener = l;
    }

    public void saveInstanceState(Bundle outState) {
        if (mState != null) {
            outState.putInt(SAVED_STATE, mState.getValue());
        }
    }


    public State restoreInstanceState(Bundle savedInstanceState) {
        State state = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_STATE)) {
            int value = savedInstanceState.getInt(SAVED_STATE);
            state = StatefulLayout.State.valueToState(value);
            setState(state);
        }
        return state;
    }


    private void setupView() {
        if (mContentLayout == null) {
            mContentLayout = getChildAt(0);
            mProgressLayout = LayoutInflater.from(getContext()).inflate(mProgressLayoutId, this, false);
            mOfflineLayout = LayoutInflater.from(getContext()).inflate(mOfflineLayoutId, this, false);
            mEmptyLayout = LayoutInflater.from(getContext()).inflate(mEmptyLayoutId, this, false);
            mNoPermissionLayout = LayoutInflater.from(getContext()).inflate(mNoPermissionLayoutId, this, false);

            mPlaceholderOfflineBinding = FragmentPlaceholderOfflineBinding.bind(mOfflineLayout);
            mStatefulLayoutModel = new StatefulLayoutModel();
            mPlaceholderOfflineBinding.setViewOfflineModel(mStatefulLayoutModel);


            addView(mProgressLayout);
            addView(mOfflineLayout);
            addView(mEmptyLayout);
            addView(mNoPermissionLayout);

            setState(mInitialState);
        }
    }

    /**
     * Register observer for view reloading.
     * Do registration in onViewCreated() if possible.
     *
     * @param reloadViewObserver
     */
    public final void setupObservables(Observer<Void> reloadViewObserver) {
        if (mStatefulLayoutModel != null) {
            mStatefulLayoutModel.setReloadObserver(reloadViewObserver);
        }
    }

}
