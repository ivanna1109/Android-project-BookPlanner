package com.example.bookplanner.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int bookID;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "wishBook")
    public int wishBook;

    @ColumnInfo(name = "currRead")
    public int currRead;

    @ColumnInfo(name = "readed")
    public int readed;

    @ColumnInfo(name = "userID")
    public int userID;

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

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

    public int getWishBook() {
        return wishBook;
    }

    public void setWishBook(int wishBook) {
        this.wishBook = wishBook;
    }

    public int getCurrRead() {
        return currRead;
    }

    public void setCurrRead(int currRead) {
        this.currRead = currRead;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
