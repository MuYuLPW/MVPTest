package com.kyty.mvptest.mvp;

import java.util.Map;

public interface ChildContract {
    interface ChildView extends IView{
        void ShowLoadding();
        void HideLoadding();
        void setError(String error);
        void setSuccess(String string);
    }

    interface ChildPresent extends IPresenter{
        void getMianInfo(Map<String,String> map);
    }

}
