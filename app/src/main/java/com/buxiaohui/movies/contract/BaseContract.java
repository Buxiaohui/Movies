package com.buxiaohui.movies.contract;

public interface BaseContract {
    interface View<P extends Presenter> {
        void setPresenter(P presenter);

        android.view.View getRootView();
    }

    interface Presenter<P extends View> {
        void bindView(P view);

        void onCreate();

        void onDestroy();
    }
}
