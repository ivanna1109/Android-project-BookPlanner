package com.example.bookplanner.aboutapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookplanner.R;

import java.util.ArrayList;

public class AboutAppListFragment extends ListFragment {

    OnAppOptionSelectedListener callBackListener;

    public interface OnAppOptionSelectedListener {
        public void onMeniOptionSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        ArrayList<String> meniOptions = new ArrayList<String>();
        meniOptions.add("How to use app");
        meniOptions.add("Why to use app");
        meniOptions.add("Sponsors");
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, meniOptions));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity().getFragmentManager().findFragmentById(R.id.fragment_information) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            callBackListener = (OnAppOptionSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        callBackListener.onMeniOptionSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }

}