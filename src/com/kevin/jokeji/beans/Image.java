package com.kevin.jokeji.beans;

import java.io.Serializable;

/**
 * Created by kevin on 17/3/1.
 */

public class Image implements Serializable{
    private String icon;
    private String author;
    private String levelIcon;
    private String date;
    private String title;
    private String image;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "icon='" + icon + '\'' +
                ", author='" + author + '\'' +
                ", levelIcon='" + levelIcon + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
