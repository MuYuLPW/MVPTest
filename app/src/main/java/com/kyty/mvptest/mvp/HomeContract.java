package com.kyty.mvptest.mvp;

/**
 * 首页接口
 */
public interface HomeContract {
    interface HomeView extends IView{
        void ShowLoadding();
        void HideLoadding();
        void setError(String error);
        void setSuccess(String string);
    }

    interface HomePresent extends IPresenter{
        void getMianInfo(String is);
    }
}
