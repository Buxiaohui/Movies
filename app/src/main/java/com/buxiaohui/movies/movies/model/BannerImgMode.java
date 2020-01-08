package com.buxiaohui.movies.movies.model;

import androidx.annotation.DrawableRes;

import com.buxiaohui.movies.R;

public class BannerImgMode {
    private String imgUrl;
    @DrawableRes
    private int imgResId;

    public int getImgResId() {
        if (imgResId <= 0) {
            return R.drawable.img_load_failure;
        }
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public BannerImgMode() {
    }

    public BannerImgMode(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
