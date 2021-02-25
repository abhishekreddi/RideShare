package com.rideshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.MyPostRidesAdapter;
import com.rideshare.adapters.SearchAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.MyPostRidesPojo;
import com.rideshare.models.ResponseData;
import com.rideshare.models.SearchDetailsPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchrideActivity extends AppCompatActivity {
    List<SearchDetailsPojo> searchDetailsPojo;
    MyPostRidesAdapter myPostRidesAdapter;
    ListView list_view;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String session,from,to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchride);

        getSupportActionBar().setTitle("Search Ride");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");


        list_view=(ListView)findViewById(R.id.list_view);

        searchDetailsPojo=new ArrayList<>();
        mysearchrides();


    }
    public void mysearchrides(){
        progressDialog = new ProgressDialog(SearchrideActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<SearchDetailsPojo>> call = service.searchrides(session,getIntent().getStringExtra("from"),getIntent().getStringExtra("to"),getIntent().getStringExtra("date"));

        call.enqueue(new Callback<List<SearchDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsPojo>> call, Response<List<SearchDetailsPojo>> response) {
                //Toast.makeText(SearchrideActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(SearchrideActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    searchDetailsPojo = response.body();
                    list_view.setAdapter(new SearchAdapter(searchDetailsPojo, SearchrideActivity.this));

                }
            }
            @Override
            public void onFailure(Call<List<SearchDetailsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchrideActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}