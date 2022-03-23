package com.example.bookplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookplanner.BooksListActivity;
import com.example.bookplanner.R;
import com.example.bookplanner.classes.Item;
import com.example.bookplanner.classes.ListAdapter;
import com.example.bookplanner.database.Book;
import com.example.bookplanner.database.User;
import com.example.bookplanner.database.UserRepository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadingBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingBookFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView readingView;
    private Button buttonReaded;
    private Button buttonToRead;
    ArrayList<Item> readingBooksList;
    private UserRepository userRep;
    private List<Book> allReadingBooks;
    private User currUser;
    private ListAdapter adapter;

    public ReadingBookFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadingBookFragment newInstance(String param1, String param2) {
        ReadingBookFragment fragment = new ReadingBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading_book, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        userRep = new UserRepository(getContext());
        currUser = userRep.getLoggedUser();
        readingView = view.findViewById(R.id.listBooks);
        buttonReaded = view.findViewById(R.id.fragmentReadedButton);
        buttonToRead = view.findViewById(R.id.fragmentToReadButton);

        readingBooksList = new ArrayList<Item>();
        allReadingBooks = userRep.getCurrReadingBooks(currUser.userID);
        addBooksToList();
        adapter = new ListAdapter(getContext(), R.layout.list_item, readingBooksList);
        readingView.setAdapter(adapter);
        readingView.setOnItemClickListener(this);

        //content();

        buttonReaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Reading books", "Kliknulo se dugme u fragmentu!");
                replaceFragment(new ReadedBookFragment());
            }
        });
        buttonToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new WishBookFragment());
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        content();
    }

        private void content(){
        readingBooksList = new ArrayList<Item>();
        allReadingBooks = userRep.getCurrReadingBooks(currUser.userID);
        addBooksToList();
        adapter = new ListAdapter(getContext(), R.layout.list_item, readingBooksList);
        readingView.setAdapter(adapter);
        //refreshContent(1000);
    }
//
//    private void refreshContent(int num) {
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                content();
//            }
//        };
//        if ( userRep.getLoggedUser() != null)
//            handler.postDelayed(runnable, num);
//    }

    private void addBooksToList() {
        if (allReadingBooks != null && allReadingBooks.size() > 0){
            for (Book book: allReadingBooks){
                Item newItem = new Item();
                newItem.setTitle(book.getTitle());
                newItem.setAuthor(book.getAuthor());
                readingBooksList.add(newItem);
            }
        } else {
            Log.i("Reading book activity", "Nema reading knjiga");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item clickedItem = adapter.getItem(position);
        Book chosenBook = userRep.getBookByTitle(clickedItem.getTitle());
        if (chosenBook != null ){
            chosenBook.setCurrRead(0);
            chosenBook.setReaded(1);
            userRep.updateBook(chosenBook);
            Toast.makeText(getContext(), "The chosen book is moved to readed section!", Toast.LENGTH_LONG).show();
            onResume();
        }
    }

    private void replaceFragment(Fragment currFragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, currFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}