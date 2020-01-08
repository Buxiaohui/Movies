package com.buxiaohui.movies.main;

import androidx.fragment.app.Fragment;

public class MainPageModel {
    private String title;
    private Class clazz;
    private Fragment fragment;
    public MainPageModel(String title, Class clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public MainPageModel(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Class getClazz() {
        return clazz;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
