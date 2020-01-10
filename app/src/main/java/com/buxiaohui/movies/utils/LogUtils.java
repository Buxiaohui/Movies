package com.buxiaohui.movies.utils;

import android.util.Log;
import androidx.annotation.NonNull;

public class LogUtils {
    public static void d(@NonNull String tag, String content) {
        Log.d(tag, content);
    }

    public static void e(@NonNull String tag, String content) {
        Log.e(tag, content);
    }
}
