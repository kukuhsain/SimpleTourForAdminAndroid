package com.kukuhsain.simpletour.admin.model.pojo;

/**
 * Created by kukuh on 08/10/16.
 */

public class Package {
    private String name;
    private String description;
    private String imageUrl;
    private String location;
    private Double price;

    public Package(String description, String imageUrl, String location, String name, Double price) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.name = name;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
