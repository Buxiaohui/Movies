package com.buxiaohui.movies.movies;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Config {
    public static final String BASE_URL = "http://www.omdbapi.com/";
    public static final String KEY = "50f53f70";
    @IntDef({
            RequestActionRet.SUCCESS,
            RequestActionRet.ID_INVALID,
            RequestActionRet.UNKNOWN,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestActionRet {
        int SUCCESS = 666;
        int UNKNOWN = 1024;
        int ID_INVALID = 1025;
    }
}
