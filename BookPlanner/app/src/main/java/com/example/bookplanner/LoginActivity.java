package com.example.bookplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserRepository;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Button logInButton;
    private Button newAccButton;
    private UserRepository userRepository;
    private List<User> allUsers;
    private boolean successful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.usernameText);
        passwordField = findViewById(R.id.passwordText);
        logInButton = findViewById(R.id.buttonLogIn);
        newAccButton = findViewById(R.id.buttonCreateAcc);

        userRepository = new UserRepository(getApplicationContext());
        allUsers = userRepository.getUsers();
        successful = false;

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ //proveravamo ulogovanost
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if (allUsers != null){
                    for(User u: allUsers){
                        if (u.username.equals(username) && u.password.equals(password)){
                            u.setLoggedIn(1);
                            userRepository.updateLoggedIn(u);
                            successful = true;
                            break;
                        }
                    }
                }
                if (successful){
                    Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(home);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Your login attempt was not successful.\nPlease try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        newAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ //idemo na create new account
                Intent registerInt = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerInt);
            }
        });
    }
}