package com.example.bookplanner.aboutapp;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bookplanner.BooksListActivity;
import com.example.bookplanner.R;
import com.example.bookplanner.fragments.ReadingBookFragment;

public class AboutAppActivity extends FragmentActivity implements AboutAppListFragment.OnAppOptionSelectedListener{

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about_app);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            AboutAppListFragment mainFragment = new AboutAppListFragment();
            mainFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
        }

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reading = new Intent(getApplicationContext(), BooksListActivity.class);
                startActivity(reading);
                finish();
            }
        });
    }

    public void onMeniOptionSelected(int position) {
        InformationFragment articleFrag = (InformationFragment) getSupportFragmentManager().findFragmentById(R.id.information_fragment);
        if (articleFrag != null) {
            articleFrag.updateInformationView(position);
        } else {
            InformationFragment newFragment = new InformationFragment();
            Bundle args = new Bundle();
            args.putInt(InformationFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}