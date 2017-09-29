package com.hins.viewpagertest;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Story {

    private String title;
    private String url;

    public Story(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
