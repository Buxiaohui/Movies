package com.buxiaohui.movies.movies.contract;

import com.buxiaohui.movies.contract.BaseContract;
import com.buxiaohui.movies.Config;
import com.buxiaohui.movies.movies.model.MovieBannerModel;
import com.buxiaohui.movies.net.HttpHelper;

public interface MoviesContract {
    interface View extends BaseContract.View<Presenter>{
       void onSuccess(MovieBannerModel movieBannerModel);
       void onFailure(String desc);
    }

    interface Fetcher {
        @Config.RequestActionRet
        int requestSingleById(String id, HttpHelper.NetListener listener);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        MovieBannerModel getData();

        int request();
    }
}
