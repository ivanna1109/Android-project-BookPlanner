package com.example.bookplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.bookplanner.R;
import com.example.bookplanner.classes.Item;
import com.example.bookplanner.classes.ListAdapter;
import com.example.bookplanner.database.Book;
import com.example.bookplanner.database.UserRepository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadedBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadedBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView readingView;
    private Button buttonReading;
    private UserRepository userRep;
    private ArrayList<Item> readedBooksList;
    private ListAdapter adapter;
    private List<Book> allReadedBooks;

    public ReadedBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadedBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadedBookFragment newInstance(String param1, String param2) {
        ReadedBookFragment fragment = new ReadedBookFragment();
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
        View view = inflater.inflate(R.layout.fragment_readed_book, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        readingView = view.findViewById(R.id.listReadedBooks);
        buttonReading = view.findViewById(R.id.fragmentReadingButton);
        userRep = new UserRepository(getContext());
        readedBooksList = new ArrayList<Item>();
        allReadedBooks = userRep.getReadedBooks(userRep.getLoggedUser().userID);
        addBooksToList();

        adapter = new ListAdapter(getContext(), R.layout.list_item, readedBooksList);
        readingView.setAdapter(adapter);

        buttonReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ReadingBookFragment());
            }
        });
    }

    private void addBooksToList() {
        if (allReadedBooks != null && allReadedBooks.size() > 0){
            for (Book book: allReadedBooks){
                Item newItem = new Item();
                newItem.setTitle(book.getTitle());
                newItem.setAuthor(book.getAuthor());
                readedBooksList.add(newItem);
            }
        } else {
            Log.i("Readed book activity", "Nema Readed knjiga");
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