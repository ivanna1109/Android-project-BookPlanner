package com.example.bookplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username LIKE :name")
    User findByName(String name);

    @Query("SELECT * FROM user WHERE loggedIn = 1")
    User findLoggedUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Query("SELECT * from user WHERE userID = :uID")
    List<UserWithBooks> getUserBooks(int uID);

    @Update
    void updateLoggedIn(User u);

    @Delete
    void delete(User user);

}
