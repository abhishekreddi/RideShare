package com.rideshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.rideshare.R;

import java.util.List;

public class MyChatRequestActivity extends AppCompatActivity {

    ListView list_view;
    ProgressDialog progressDialog;
   // List<msgs> a1;
    SharedPreferences sharedPreferences;

    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_request);
    }
}