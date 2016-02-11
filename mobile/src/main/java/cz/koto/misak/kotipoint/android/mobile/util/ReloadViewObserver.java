package cz.koto.misak.kotipoint.android.mobile.util;

import rx.Observer;

/**
 * Observer to be used to trigger view reloading.
 * For example reload triggered from placeholder layout.
 *
 * @param <T>
 */
public abstract class ReloadViewObserver<T> implements Observer<T> {
    @Override
    public void onNext(T o) {

    }
}