package com.buxiaohui.movies.movies.model;

import java.io.Serializable;

public class ActorModel implements Serializable {
    private String imgUrl;

    public ActorModel() {
    }

    public ActorModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
