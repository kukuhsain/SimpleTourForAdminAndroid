package com.kukuhsain.simpletour.host.model.pojo;

/**
 * Created by kukuh on 08/10/16.
 */

public class Destination {
    private String title;
    private String content;
    private String imageUrl;
    private String location;

    public Destination() {
    }

    public Destination(String title, String content, String imageUrl, String location) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
