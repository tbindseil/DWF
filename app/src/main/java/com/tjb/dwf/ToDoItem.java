package com.tjb.dwf;

public class ToDoItem {
    private String id;
    private String text;
    private Boolean complete;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
