package com.example.bookplanner.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bookplanner.R;
import com.example.bookplanner.database.Book;
import com.example.bookplanner.database.UserRepository;

public class WishBookMenu extends Activity {

    private Button deleteBook;
    private Button moveBook;
    private Book chosenBook;
    private UserRepository userRep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_book_menu);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //80% naseg ekrana
        getWindow().setLayout((int) (width * .8), (int) (height * .1));

        userRep = new UserRepository(getApplicationContext());
        findChosenBook();
        deleteBook = findViewById(R.id.deleteBtn);
        moveBook = findViewById(R.id.moveBtn);

        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRep.deleteBook(chosenBook);
                Toast.makeText(getApplicationContext(), "The chosen book has been deleted!!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        moveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenBook.setCurrRead(1);
                chosenBook.setWishBook(0);
                userRep.updateBook(chosenBook);
                Toast.makeText(getApplicationContext(), "The chosen book is moved to reading section!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void findChosenBook() {
        String title = getIntent().getStringExtra("bookTitle");
        chosenBook = userRep.getBookByTitle(title);
        if (chosenBook != null){
            Log.i("WishMenu: ", "Pronadjena je knjiga u bazi.");
        } else {
            Log.i("WishMenu: ", "Nema knjige u bazi.");
        }
    }


}
