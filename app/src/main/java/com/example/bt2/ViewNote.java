package com.example.bt2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ViewNote extends AppCompatActivity{
    private static EditText edv_title;
    private static EditText edv_content;
    private  String mDate;
    private static final String TAG = "BT2Log";
    public  int requestcode = 0;
    Bundle bundle;
    public  int pos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlenoteview);
        edv_title =  findViewById(R.id.edv_title);
        edv_content = findViewById(R.id.edv_content);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            requestcode = bundle.getInt("requestcode");
            pos = bundle.getInt("pos");
        }
        if(requestcode == 2){
            String title = bundle.getString("title");
            String text = bundle.getString("text");
            edv_title.setText(title);
            edv_content.setText(text);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewnotemenu, menu);
        return true;
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    private void setDefaultDateTime() {
        Date currentTime = Calendar.getInstance().getTime();
        mDate = currentTime.toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                if(requestcode == 2 && pos!=-1){
                    Intent data = new Intent();
                    data.putExtra("delete",1);
                    data.putExtra("pos",pos);
                    setResult(RESULT_OK,data);
                    finish();
                    return true;
                }
                else
                    return true;
            case R.id.save:
                if(CheckNoteText()&&CheckNoteTitle()){
                    setDefaultDateTime();
                    Intent data = new Intent();
                    data.putExtra("delete",0);
                    data.putExtra("pos",pos);
                    Note.packageIntent(data, edv_title.getText().toString(), mDate, edv_content.getText().toString());
                    setResult(RESULT_OK,data);
                    finish();
                    return true;
                }
        }
        return true;
    }
    public void Dialog(String Message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Error!");
        builder1.setMessage(Message);
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    private boolean CheckNoteTitle() {
        if(edv_title.getText().toString().equals("")) {
            Dialog("Title can not be NULL");
            return false;
        }
        else
            return true;

    }
    private boolean CheckNoteText() {
        if(edv_content.getText().toString().equals("")) {
            Dialog("Content can not be NULL");
            return false;
        }
        else
            return true;
    }

}