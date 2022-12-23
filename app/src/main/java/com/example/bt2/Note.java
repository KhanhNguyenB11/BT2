package com.example.bt2;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class Note {
    public String mTitle;
    public String mModify_date;
    public String mText;

    public final static String Title = "Title";
    public final static String Modify_date = "date";
    public final static String Text = "Enter your notes here!";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm", Locale.US);
    public static final String ITEM_SEP = System.getProperty("line.separator");
    public Note(String title, String Modify_date, String text) {
        this.mTitle = title;
        this.mModify_date = Modify_date;
        this.mText = text;
    }

    Note (Intent intent){
        mTitle = intent.getStringExtra(Note.Title);
        mText = intent.getStringExtra(Note.Text);
        mModify_date = intent.getStringExtra(Note.Modify_date);
    }
    public static void packageIntent(Intent intent, String title, String date, String text) {

        intent.putExtra(Note.Title, title);
        intent.putExtra(Note.Modify_date, date);
        intent.putExtra(Note.Text, text);
    }
    public String getTitle() {
        return mTitle;
    }

    public String getModify_date() {
        return mModify_date;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setModify_date(String modify_date) {
        mModify_date = modify_date;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }
    public String toString() {
        return mTitle + ITEM_SEP + mModify_date + ITEM_SEP + mText;
    }

}
