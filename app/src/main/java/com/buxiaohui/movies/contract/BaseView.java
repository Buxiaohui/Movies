package com.buxiaohui.movies.contract;

import android.view.View;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    View getRootView();
}
