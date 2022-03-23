package com.example.bookplanner.aboutapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookplanner.R;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    final static String ARG_POSITION="position";
    int currPosition = -1;
    private TextView aboutAppInfo;
    private ArrayList<String> informations;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null){
            currPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        informations = new ArrayList<String>();
        generateInformations();

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        aboutAppInfo = (TextView) view.findViewById(R.id.appInfo);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateInformationView(args.getInt(ARG_POSITION));
        } else if (currPosition!= -1) {
            updateInformationView(currPosition);
        }
    }

    public void updateInformationView(int position) {
        Log.e("Information fragment", "Kliknuo je retard: "+position);
        aboutAppInfo.setText(informations.get(position));
        currPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, currPosition);
    }

    private void generateInformations() {
        informations.add("This application is pretty much simple for usage. \n" +
                "First of all, you need to create an account and after that, you can log in and start to use this BookPlanner.\n" +
                "This application has 3 sections: Books to read, Reading books and readed books.\n" +
                "In \"Books to read\" section, you can add a book which you want to read in the future. Also, if you change your mind,\n" +
                "you can delete selected book from that list. When you want to start reading some book from this list, on button click,\n" +
                "you can move it to the second section called \"Reading books\".\n" +
                "In this \"Reading books\" section are books what you are currently read. By clicking on selected book, you move it to\n" +
                "the third section \"Readed books\".\n" +
                "In the last section \"Readed books\" are books that you have read.\n" +
                "From every of these section you can switch to another in logically order (from Books to read to Reading books and vise versa, etc.).\n" +
                "Also, on the action bar, you have many with five options: Show bookstores on map, Get/Start music, Get Quote, About app and Log out.\n" +
                "When you click option \"Show bookstores on map\", you can see a new window with a map in which you can see your current location\n" +
                "and nearest bookstores.\n" +
                "\"Get/Start music\" option allowes you to listen to music while you are doing something else, and you can stop the music\n" +
                "by clicking again on these option or by shaking your fone.\n" +
                "\"Get Quote\" option shows you some random quote from Game of Thrones books which this application gets from Rest service.\n" +
                "\"About app\" option contains basic informations about this app.\n" +
                "When you click \"Log out\" option, you are not logged in this app anymore.\n" +
                "We hope these guadlines will help you to use this amazing app!");
        informations.add("This application is intended for those who are book lovers and want to keep records about their desirable books,\n" +
                "readed books and currently reading books.");
        informations.add("First of all, we want to thank to all cups of coffee beacuse without them, launcing this application would be impossible!\n" +
                "So, because of that, our special sponsor is Nescafe and Grand cafe!");
    }
}