package com.example.EventManagingWebsiteInSpring.controller;

public class SearchFieldDTO {
    String nameKey;
    String availableStart;
    String availableEnd;
    int capacity;

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getAvailableStart() {
        return availableStart;
    }

    public void setAvailableStart(String availableStart) {
        this.availableStart = availableStart;
    }

    public String getAvailableEnd() {
        return availableEnd;
    }

    public void setAvailableEnd(String availableEnd) {
        this.availableEnd = availableEnd;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public SearchFieldDTO(String nameKey, String availableStart, String availableEnd, int capacity) {
        this.nameKey = nameKey;
        this.availableStart = availableStart;
        this.availableEnd = availableEnd;
        this.capacity = capacity;
    }
}
