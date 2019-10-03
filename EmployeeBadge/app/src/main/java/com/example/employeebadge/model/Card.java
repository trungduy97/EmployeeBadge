package com.example.employeebadge.model;

import org.parceler.Parcel;

/**
 * @author Created by duydt on 8/20/2019.
 */
@Parcel public class Card {
    private String name;
    private String id;
    private String position;
    private String path;
    private int pos;

    public Card() {}

    public Card(String name, String id, String position, String path) {
        this.name = name;
        this.id = id;
        this.position = position;
        this.path = path;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
