package com.example.bookplanner.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bookplanner.database.AppDatabase;
import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserDao;
import com.example.bookplanner.retrofit.GoTQuote;
import com.example.bookplanner.retrofit.RestApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuClass {

    private UserDao userDao;
    private List<User> allUsers;

    public MenuClass(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        userDao = db.userDao();
        allUsers = userDao.getAll();
    }

    //prva opcija - start/ stop music
    public void startStopMusic(){

    }


    //treca opcija - loggedOut
    public boolean logOut(){
        User u = userDao.findLoggedUser();
        u.loggedIn = 0;
        userDao.updateLoggedIn(u);
        return true;
    }
}
