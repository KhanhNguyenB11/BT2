package com.example.bt2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private final List<Note> Notelist = new ArrayList<Note>();
    private final Context mcontext;
    private static final String TAG = "BT2Log";

    public NoteAdapter(Context context) {
        mcontext = context;
    }

    public void add(Note note) {

        Notelist.add(note);
        notifyDataSetChanged();

    }
    public void set(int i,Note n){
        Notelist.set(i,n);
    }

    public void clear() {
        Notelist.clear();
        notifyDataSetChanged();

    }
    public void remove(int i){
        Log.i("remove", String.valueOf(i));
        Notelist.remove(i);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Notelist.size();
    }

    @Override
    public Object getItem(int i) {
        return Notelist.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Note note = (Note) getItem(i);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView itemLayout = (CardView) inflater.inflate(R.layout.noteview, null);
        final TextView titleView = itemLayout.findViewById(R.id.tv_title);
        titleView.setText(note.getTitle());
        final TextView dateView = itemLayout.findViewById(R.id.tv_date);
        dateView.setText("Last Modified: " + note.getModify_date());
        final TextView contentView = itemLayout.findViewById(R.id.tv_content);
        contentView.setText(note.getText());
        return  itemLayout;
    }
}
