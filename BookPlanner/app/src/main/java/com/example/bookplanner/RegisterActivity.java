package com.example.bookplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserRepository;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button createAcc;
    private List<User> allUsers;
    private CheckBox gotFan;
    private EditText username;
    private EditText password;
    private UserRepository userRep;
    private boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRep = new UserRepository(getApplicationContext());
        allUsers = userRep.getUsers();

        username = findViewById(R.id.newUsernameText);
        password = findViewById(R.id.newPassText);
        gotFan = findViewById(R.id.gotFan);
        createAcc = findViewById(R.id.createAccButton);
        exists = false;

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = username.getText().toString();
                String newPassword = password.getText().toString();
                boolean isGotFan = gotFan.isChecked();
                if (allUsers != null) {
                    for (User u : allUsers) {
                        if (u.username == newUsername) {
                            exists = true;
                            CharSequence text = "There is already user with this username!\nTry another username";
                            int duration = Toast.LENGTH_LONG;
                            Toast.makeText(getApplicationContext(), text, duration).show();
                            break;
                        }
                    }
                }
                if (!exists) {
                    User newUser = new User();
                    newUser.setUserId(0);
                    newUser.setUsername(newUsername);
                    newUser.setPassword(newPassword);
                    newUser.setGotFan(isGotFan? 1 : 0);
                    newUser.setLoggedIn(0);
                    Log.i("Register activity", newUser.toString());
                    userRep.insert(newUser);
                    CharSequence text = "You are successfully registered!\nNow you can log in!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();

                    Log.i("IN register", "Uneli smo ga u bazu!");
                    Intent logIn = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logIn);
                    finish();
                }

            }
        });
    }
}