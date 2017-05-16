package com.example.adsg1.newsgateway;

import java.io.Serializable;

/**
 * Created by adsg1 on 5/5/2017.
 */

public class Article implements Serializable {

    String author;
    String title;
    String description;
    String urtlToImage;
    String publishedAt;
    String url;
    String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrtlToImage() {
        return urtlToImage;
    }

    public void setUrtlToImage(String urtlToImage) {
        this.urtlToImage = urtlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
