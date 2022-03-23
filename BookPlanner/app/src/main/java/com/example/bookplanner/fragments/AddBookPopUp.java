package com.example.bookplanner.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bookplanner.R;
import com.example.bookplanner.database.Book;
import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserRepository;

public class AddBookPopUp extends Activity {

    private EditText bookTitle;
    private EditText bookAuthor;
    private Button addButton;
    private String loggedUsername;
    private UserRepository userRep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_book);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //80% naseg ekrana
        getWindow().setLayout((int) (width*.8), (int) (height*.5));

        userRep = new UserRepository(getApplicationContext());
        loggedUsername = userRep.getLoggedUser().username;

        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        addButton = findViewById(R.id.addNewBookClick);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bookTitle.getText().toString();
                String author = bookAuthor.getText().toString();

                Book newBook = new Book();
                newBook.setTitle(title);
                newBook.setAuthor(author);
                newBook.setWishBook(1);
                newBook.setReaded(0);
                newBook.setCurrRead(0);
                User currUser = userRep.getUser(loggedUsername);
                if ( currUser != null){
                    newBook.setUserID(currUser.userID);
                }
                userRep.insertBook(newBook);
                Toast.makeText(getApplicationContext(), "A new book has been aded!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
