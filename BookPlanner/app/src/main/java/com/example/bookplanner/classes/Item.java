package com.example.bookplanner.classes;

public class Item {

    private String title;
    private String author;
    private boolean wishBook;
    private boolean currRead;
    private boolean readed;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isWishBook() {
        return wishBook;
    }

    public void setWishBook(boolean wishBook) {
        this.wishBook = wishBook;
    }

    public boolean isCurrRead() {
        return currRead;
    }

    public void setCurrRead(boolean currRead) {
        this.currRead = currRead;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }
}
