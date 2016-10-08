package com.kukuhsain.simpletour.model.pojo;

/**
 * Created by kukuh on 08/10/16.
 */

public class Destination {
    private String name;
    private String description;
    private String imageUrl;
    private String location;

    public Destination() {
    }

    public Destination(String name, String description, String imageUrl, String location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
