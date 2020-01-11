package com.buxiaohui.movies.utils;

import com.buxiaohui.movies.BuildConfig;

import android.util.Log;
import androidx.annotation.NonNull;

public class LogUtils {
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static void d(@NonNull String tag, String content) {
        if (DEBUG) {
            Log.d(tag, content);
        }
    }

    public static void e(@NonNull String tag, String content) {
        Log.e(tag, content);
    }
}
