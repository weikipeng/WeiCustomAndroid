package com.jzb.android.rx;

import com.jzb.common.LogTrackTool;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 封装Subscriber
 * Created by WikiPeng on 2016/12/16 14:19.
 */
public abstract class JZBSubscriber<T> implements Observer<T> {
    public static final int FLAG_NOT_ON_ERROR_COMPLETED = 1;
    protected int flag;

    public JZBSubscriber() {

    }

    public JZBSubscriber(int flag) {
        this.flag = flag;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(Throwable e) {
        //        Log.e("Network",e.getMessage(),e);
        //        ACRA.getErrorReporter().handleException(e);
        if (e != null) {
            e.printStackTrace();
            //LogTrackTool.getInstance().t(e.getMessage());
        }

        int tFlag = flag & FLAG_NOT_ON_ERROR_COMPLETED;
        if (tFlag != 1) {
            onComplete();
        }
    }

    @Override
    public void onComplete() {

    }
}