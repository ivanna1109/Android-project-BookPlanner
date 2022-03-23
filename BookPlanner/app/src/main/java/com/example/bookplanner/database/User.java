package com.example.bookplanner.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userID;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "gotFan")
    public int gotFan;

    @ColumnInfo(name = "loggedIn")
    public int loggedIn;

    public void setUserId(int id){
        this.userID = id;
    }
    public void setUsername(String u){
        this.username = u;
    }

    public void setPassword(String p){
        this.password = p;
    }

    public void setGotFan(int g){
        this.gotFan = g;
    }

    public void setLoggedIn(int l){
        this.loggedIn = l;
    }

    @Override
    public String toString(){
        return username+" | "+password;
    }
}

