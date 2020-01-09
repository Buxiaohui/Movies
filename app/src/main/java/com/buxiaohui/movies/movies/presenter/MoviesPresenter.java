package com.buxiaohui.movies.movies.presenter;

import com.buxiaohui.movies.BuildConfig;
import com.buxiaohui.movies.contract.BasePanelPresenter;
import com.buxiaohui.movies.movies.contract.MoviesContract;
import com.buxiaohui.movies.movies.data.DataFetcher;
import com.buxiaohui.movies.movies.model.MovieBannerModel;
import com.buxiaohui.movies.net.HttpHelper;
import com.google.gson.Gson;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

public class MoviesPresenter extends BasePanelPresenter implements MoviesContract.Presenter {
    private static final String TAG = "MoviesPresenter";
    private static final int INNER_MSG_TYPE_FAIL = -1;
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int INNER_MSG_TYPE_SUCCESS = 0;
    private MoviesContract.Fetcher mDataFetcher;
    private MoviesContract.View mView;
    private MovieBannerModel mMovieBannerModel;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INNER_MSG_TYPE_FAIL:
                    if (mView != null) {
                        mView.onFailure((String) msg.obj);
                    }
                    break;
                case INNER_MSG_TYPE_SUCCESS:
                    if (mView != null) {
                        mView.onSuccess((MovieBannerModel) msg.obj);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        mDataFetcher = new DataFetcher();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void bindView(MoviesContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public MovieBannerModel getData() {
        return mMovieBannerModel;
    }

    @Override
    public int request() {
        Log.d("buxiaohui","bindView-request");
        // just test
        int actionRet = mDataFetcher.requestSingleById("tt7286456", new HttpHelper.NetListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "request,onSuccess,response:" + response);
                MovieBannerModel movieBannerModel = new Gson().fromJson(response, MovieBannerModel.class);
                mMovieBannerModel = movieBannerModel;
                Message message = Message.obtain();
                message.what = INNER_MSG_TYPE_SUCCESS;
                message.obj = movieBannerModel;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(String desc) {
                mMovieBannerModel = null;
                Message message = Message.obtain();
                message.what = INNER_MSG_TYPE_FAIL;
                message.obj = desc;
                mHandler.sendMessage(message);
            }
        });
        if (DEBUG) {
            Log.d(TAG, "request,actionRet:" + actionRet);
        }
        return actionRet;
    }
}
