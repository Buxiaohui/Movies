package com.buxiaohui.movies.movies.data;

import java.util.HashMap;
import java.util.Map;

import com.buxiaohui.movies.BuildConfig;
import com.buxiaohui.movies.Config;
import com.buxiaohui.movies.movies.contract.MoviesContract;
import com.buxiaohui.movies.net.HttpHelper;

import android.text.TextUtils;
import androidx.annotation.NonNull;

public class DataFetcher implements MoviesContract.Fetcher {
    private static final boolean DEBUG = BuildConfig.DEBUG && true;
    private HttpHelper mHttpHelper;

    public DataFetcher() {
        checkHeepHelper();
    }

    @Override
    public int requestSingleById(@NonNull String id, HttpHelper.NetListener listener) {
        if (TextUtils.isEmpty(id)) {
            return Config.RequestActionRet.ID_INVALID;
        }
        if (DEBUG) {
            listener.onSuccess(HttpHelper.debugStr);
        } else {
            checkHeepHelper();
            Map<String, String> params = new HashMap<>();
            params.put("i", id);
            mHttpHelper.get(Config.BASE_URL, params, listener);
        }
        return Config.RequestActionRet.SUCCESS;
    }

    private void checkHeepHelper() {
        if (mHttpHelper == null) {
            mHttpHelper = new HttpHelper();
        }
    }
}
