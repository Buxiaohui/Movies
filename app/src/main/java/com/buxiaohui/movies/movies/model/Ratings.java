package com.buxiaohui.movies.movies.model;

import java.io.Serializable;

public class Ratings implements Serializable {

    private String Source;
    private String Value;

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ratings{");
        sb.append("Source='").append(Source).append('\'');
        sb.append(", Value='").append(Value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
