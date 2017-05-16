package com.example.adsg1.newsgateway;

import java.io.Serializable;

/**
 * Created by adsg1 on 5/5/2017.
 */

public class NewsBean implements Serializable {

    private String channelId;
    private String channelName;
    private String channelUrl;
    private String ChannelCategory;


    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelCategory() {
        return ChannelCategory;
    }

    public void setChannelCategory(String channelCategory) {
        ChannelCategory = channelCategory;
    }

    public void setChannelId(String channelId) {

        this.channelId = channelId;
    }
}
