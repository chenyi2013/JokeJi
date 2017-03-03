package com.kevin.jokeji.beans;

import java.io.Serializable;

/**
 * Created by kevin on 17/3/2.
 */

public class Saying implements Serializable{

    String title;
    String url;

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

    @Override
    public String toString() {
        return "Saying{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
