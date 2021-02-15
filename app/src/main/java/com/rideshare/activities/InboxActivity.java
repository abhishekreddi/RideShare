package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.InboxAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.InboxPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity {
    List<InboxPojo> inboxPojos;
    SharedPreferences sharedPreferences;
    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);

        inboxPojos=new ArrayList<>();
        getReguestedRides();
    }
}