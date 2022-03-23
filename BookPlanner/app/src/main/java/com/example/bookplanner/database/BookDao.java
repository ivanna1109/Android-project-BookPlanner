package com.example.bookplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book WHERE wishBook = 1 and userID = :id")
    List<Book> getWishedBooks(int id);

    @Query("SELECT * FROM book WHERE currRead = 1 and userID = :id")
    List<Book> getCurrBooks(int id);

    @Query("SELECT * FROM book WHERE readed = 1 and userID = :id")
    List<Book> getReadedBooks(int id);

    @Query("SELECT * FROM book where title like :t")
    Book findBookByTitle(String t);

    @Update
    void updateWishBook(Book u);

    @Update
    void updateCurrRead(Book u);

    @Update
    void updateReadedBook(Book u);

    @Update
    void updateBook(Book b);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Book... books);

    @Delete
    void deleteBook(Book book);

}
