package com.kyty.mvptest.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BasePresenter<V extends IView> implements IPresenter {
    protected Reference<V> mView;

    public BasePresenter(V view) {
        this.mView = new WeakReference<V>(view);
    }

    protected V getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
    }

    /**
     * 主要用于判断IView的生命周期是否结束，防止出现内存泄露状况
     *
     * @return
     */
    protected boolean isViewAttach() {
        return mView != null && mView.get() != null;
    }

    @Override
    public void detachView() {
        if (mView!=null){
            mView.clear();
            mView=null;
        }
    }
}
