package com.example.bookplanner.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.net.ContentHandler;
import java.util.List;

public class UserRepository{

    private UserDao userDao;
    private BookDao bookDao;
    private List<User> users;
    private List<Book> books;

    public UserRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        userDao = db.userDao();
        bookDao = db.bookDao();
        users = userDao.getAll();
        books = bookDao.getAll();
        if (books != null)
            Log.i("User repository", "Broj knjiga u bazi: "+books.size());
    }

    public List<User> getUsers(){
        return users;
    }

    public List<Book> getBooks() { return books; }

    public void insert(User user1){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertUser(user1);
        });
    }

    public void updateLoggedIn(User u){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.updateLoggedIn(u);
        });
    }

    public void insertBook(Book book){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.insertAll(book);
        });
    }

    public User getUser(String username){
        return userDao.findByName(username);
    }

    public User getLoggedUser(){
        return userDao.findLoggedUser();
    }

    public List<Book> getWishedBooks(int id){
        return bookDao.getWishedBooks(id);
    }

    public List<Book> getCurrReadingBooks(int id) { return bookDao.getCurrBooks(id); }

    public List<Book> getReadedBooks(int id) { return bookDao.getReadedBooks(id); }

    public Book getBookByTitle(String t){ return bookDao.findBookByTitle(t); }

    public void deleteBook(Book b){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.deleteBook(b);
        });
    }

    public void updateBook(Book b){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.updateBook(b);
        });
    }
}
