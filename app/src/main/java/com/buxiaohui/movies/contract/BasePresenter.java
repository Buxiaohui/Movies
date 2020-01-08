package com.buxiaohui.movies.contract;

public interface BasePresenter<P extends BaseView> {
    void bindView(P view);

    void onCreate();

    void onDestroy();
}
