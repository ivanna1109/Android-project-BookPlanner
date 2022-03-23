package com.example.bookplanner.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookplanner.R;

import org.w3c.dom.Text;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Item> {

    private static final String NAME= "LIST ADAPTER: ";
    private List<Item> allBooks;

    public ListAdapter(Context context, int resource, List<Item> allBooks) {
        super(context, resource);
        this.allBooks = allBooks;
    }

    @Override
    public Item getItem(int position) {
        Item task = null;
        try {
            task = allBooks.get(position);
        } catch (IndexOutOfBoundsException e){
            Log.d(NAME, e.getMessage());
        }
        return task;
    }

    @Override
    public int getCount() {
        return allBooks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ListViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.bookInfo = (TextView) convertView.findViewById(R.id.bookInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListViewHolder) convertView.getTag();
        }
        final Item book = getItem(position);
        viewHolder.bookInfo.setText("Title: "+book.getTitle()+"\nAuthor: "+book.getAuthor());
        return convertView;
    }

    public void addTask(Item t){
        allBooks.add(t);
        notifyDataSetChanged();
    }

    public void removeTask(Item t){
        allBooks.remove(t);
        notifyDataSetChanged();
    }

    public static class ListViewHolder{

        private TextView bookInfo;

    }

    public void clear() {
        allBooks.clear();
        notifyDataSetChanged();
    }

}
