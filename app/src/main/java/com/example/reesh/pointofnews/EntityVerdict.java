package com.example.reesh.pointofnews;

public class EntityVerdict {
    private String name;
    private int imageResourceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public EntityVerdict(String name) {

        this.name = name;
    }
}
