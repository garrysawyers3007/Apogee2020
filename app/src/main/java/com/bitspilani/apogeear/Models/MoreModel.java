package com.bitspilani.apogeear.Models;

public class MoreModel {
    private String name;
    private String subName;
    private int image;

    public MoreModel(String name, int image, String subName) {
        this.name = name;
        this.image = image;
        this.subName = subName;
    }

    public MoreModel(String name, int image) {
        this.name = name;
        this.image = image;
        this.subName = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
