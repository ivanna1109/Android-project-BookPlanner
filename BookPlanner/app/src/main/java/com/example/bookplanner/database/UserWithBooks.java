package com.example.bookplanner.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithBooks {
    @Embedded public User user;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public List<Book> books;
}
