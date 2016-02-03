package it.ennova.droidcondemo.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public abstract class ObservableHeadlessFragment<T> extends Fragment {

    private Observable<T> internalObserver;
    private Subject<T, T> internalSubject = PublishSubject.create();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<T> getObservable() {
        if (internalObserver == null) {
            internalObserver = buildInternalObservable();
        }

        internalObserver.subscribe(internalSubject);
        return internalSubject.asObservable();
    }

    protected abstract Observable<T> buildInternalObservable();

    public void resetObservable() {
        internalObserver = null;
    }
}
