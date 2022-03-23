package com.example.bookplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bookplanner.classes.Item;
import com.example.bookplanner.classes.ListAdapter;
import com.example.bookplanner.classes.MenuClass;
import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private UserRepository userRep;
    private boolean loggedUser;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userRep = new UserRepository(getApplicationContext());
        loggedUser = false;
        List<User> allUsers = userRep.getUsers();

        if (allUsers == null){
            Log.i("In Home Activity", "Empty users.");
        } else {
            Log.i("In Home Activity", "There are users. Number of users: "+allUsers.size());
            for (User user: allUsers){
                if (user.loggedIn == 1){
                    loggedUser = true;
                    break;
                }
            }
        }
        //ovde dobavljamo sve usere i proveravamo da li ima neki ulogovan da ga redirectuje na njegove knjige koje trenutno cita
        if (loggedUser){ //redirektuje ga na knjige koje trenutno cita
            Log.i("Home activity","Ulogovan user!");
            Intent logIn = new Intent(this, BooksListActivity.class);
            startActivity(logIn);
            finish();
        } else { //salje ga na stranicu da se loguje
            Intent logIn = new Intent(this, LoginActivity.class);
            startActivity(logIn);
            finish();
        }
    }
}