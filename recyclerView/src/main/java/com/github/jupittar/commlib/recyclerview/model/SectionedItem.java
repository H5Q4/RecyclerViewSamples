package com.github.jupittar.commlib.recyclerview.model;

public class SectionedItem<T> {
    private boolean isHead;
    private String title;
    private T item;

    public SectionedItem(T item) {
        this.item = item;
        this.isHead = false;
        this.title = null;
    }

    public SectionedItem(boolean isHead, String title) {
        this.isHead = isHead;
        this.title = title;
        this.item = null;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
