package com.example.bt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public int addNodecode = 1;
    public int viewNodecode = 2;
    ListView lv;
    NoteAdapter adapter;
    public String FILE_NAME = "BT2_Data.txt";
    private static final String TAG = "BT2Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(getApplicationContext());
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemclick(i);
            }
        });
    }

public  void itemclick(int i){
    Note note = (Note) adapter.getItem(i);
    Intent intent2 = new Intent(this,ViewNote.class);
    intent2.putExtra("pos",i);
    intent2.putExtra("requestcode", viewNodecode);
    intent2.putExtra("title", note.getTitle());
    intent2.putExtra("text", note.getText());
    startActivityForResult(intent2,viewNodecode);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getCount() == 0)
            loadItems();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNote:
                Intent intent = new Intent(this, ViewNote.class);
                startActivityForResult(intent,addNodecode);
                return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addNodecode) {
            if (resultCode == RESULT_OK) {
                Note note = new Note(data);
                adapter.add(note);
            }
        }
        if (requestCode == viewNodecode) {
            if (resultCode == RESULT_OK) {
                Note note = new Note(data);
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    int pos = bundle.getInt("pos");
                    int delete = bundle.getInt("delete");
                    if(delete == 1){
                            adapter.remove(pos);
                    }
                    else
                        adapter.set(pos,note);

                }
            }
        }
        saveItems();
    }
    private void loadItems() {
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));
            String title = null;
            String text = null;
            String date = null;
            while (null != (title = reader.readLine())) {
                date = reader.readLine();
                text = reader.readLine();
                adapter.add(new Note(title, date, text));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));
            for (int idx = 0; idx < adapter.getCount(); idx++) {
                writer.println(adapter.getItem(idx).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}