package com.example.employeebadge.model;

import org.parceler.Parcel;

/**
 * @author Created by duydt on 8/20/2019.
 */
@Parcel public class Card {
    private String name;
    private String id;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
