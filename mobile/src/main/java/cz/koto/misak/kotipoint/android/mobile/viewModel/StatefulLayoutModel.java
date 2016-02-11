package cz.koto.misak.kotipoint.android.mobile.viewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Subclassing BaseObservable allows us to use @Bindable on getters, and notifyPropertyChanged() when a @Bindable property changes.
 */
public class StatefulLayoutModel extends BaseObservable {


    private Observer<Void> mReloadObserver;
    private Observable<Void> reloadViewTriggerObservable;

    public StatefulLayoutModel(/*Observable<Void> observable, Observer<Void> observer*/) {
//        mReloadObservable = observable;
//        mReloadObserver = observer;

        reloadViewTriggerObservable = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                // Do the work and call onCompleted when you done,
                // no need to call onNext if you have nothing to emit
                subscriber.onCompleted();
            }
        });
    }

    public void setReloadObserver(@NonNull  Observer<Void> mReloadObserver) {
        this.mReloadObserver = mReloadObserver;
    }

    public View.OnClickListener callRequestContent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReloadObserver == null) {
                    Context context = v.getContext();
                    Timber.e("Unable to locate observer for triggering reloadViewObservable");
                } else {
                    reloadViewTriggerObservable.subscribe(mReloadObserver);
                }
            }
        };
    }

}
