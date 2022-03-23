package com.example.bookplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
 * Use the {@link WishBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView wishBookView;
    private Button buttonReading;
    private Button addNewBook;
    private UserRepository userRep;
    private ArrayList<Item> wishBooksList;
    private List<Book> allWishBooks;
    private User currUser;
    private ListAdapter listAdapter;

    public WishBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishBookFragment newInstance(String param1, String param2) {
        WishBookFragment fragment = new WishBookFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_book, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRep = new UserRepository(getContext());
        currUser = userRep.getLoggedUser();
        wishBookView = view.findViewById(R.id.wishBooksList);
        buttonReading = view.findViewById(R.id.readBooksButton);
        addNewBook = view.findViewById(R.id.addNewBookBtn);
        wishBooksList = new ArrayList<Item>();
        allWishBooks = userRep.getWishedBooks(currUser.userID);
        addBooksToList();

        listAdapter = new ListAdapter(getContext(), R.layout.list_item, wishBooksList);
        wishBookView.setAdapter(listAdapter);

        //content();

        buttonReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ReadingBookFragment());
            }
        });
        addNewBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("WishBook fragment", "Kliknuto na novu knjigu");
                Intent popUp = new Intent(getActivity(), AddBookPopUp.class);
                startActivity(popUp);
            }
        });

        wishBookView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item clickedItem = listAdapter.getItem(position);
                Intent menuPopUp = new Intent(getActivity(), WishBookMenu.class);
                menuPopUp.putExtra("bookTitle", clickedItem.getTitle());
                startActivity(menuPopUp);
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

    private void addBooksToList() {
        if (allWishBooks != null && allWishBooks.size() > 0){
            for (Book book: allWishBooks){
                Item newItem = new Item();
                newItem.setTitle(book.getTitle());
                newItem.setAuthor(book.getAuthor());
                wishBooksList.add(newItem);
            }
        } else {
            Log.i("Wish book activity", "Nema wish knjiga");
        }
    }

    private void content(){
        wishBooksList = new ArrayList<Item>();
        allWishBooks = userRep.getWishedBooks(currUser.userID);
        addBooksToList();
        listAdapter = new ListAdapter(getContext(), R.layout.list_item, wishBooksList);
        wishBookView.setAdapter(listAdapter);
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