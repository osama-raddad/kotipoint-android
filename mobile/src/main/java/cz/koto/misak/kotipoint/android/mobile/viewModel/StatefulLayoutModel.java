package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;

/**
 * Subclassing BaseObservable allows us to use @Bindable on getters, and notifyPropertyChanged() when a @Bindable property changes.
 */
public class StatefulLayoutModel extends BaseObservable {


    private Observable<String> mReloadObservable;
    private Observer<String> mReloadObserver;

    public StatefulLayoutModel(/*Observable<Void> observable, Observer<Void> observer*/) {
//        mReloadObservable = observable;
//        mReloadObserver = observer;
    }

    public void setmReloadObservable(Observable<String> mReloadObservable) {
        this.mReloadObservable = mReloadObservable;
    }

    public void setmReloadObserver(Observer<String> mReloadObserver) {
        this.mReloadObserver = mReloadObserver;
    }

    public View.OnClickListener callRequestContent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReloadObservable == null) {
                    Context context = v.getContext();
                    Toast.makeText(context, "TODO IMPLEMENT @Observable to call StatefulPermissionFragment.requestContent", Toast.LENGTH_SHORT).show();
                } else {
                    mReloadObservable.subscribe(mReloadObserver);
                }
            }
        };
    }

}
