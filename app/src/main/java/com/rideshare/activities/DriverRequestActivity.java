package com.rideshare.activities;

import android.app.ProgressDialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.msgs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverRequestActivity extends AppCompatActivity {

    ListView list_view;
    ProgressDialog progressDialog;
    List<msgs> a1;
    MyRequestAdapter myRequestAdapter;
    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_request);
        getSupportActionBar().setTitle("My Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<msgs>> call = service.myrequests(session);
        call.enqueue(new Callback<List<msgs>>() {
            @Override
            public void onResponse(Call<List<msgs>> call, Response<List<msgs>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(DriverRequestActivity.this,"No data found", Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    myRequestAdapter=new MyRequestAdapter(a1, DriverRequestActivity.this);  //attach adapter class with therecyclerview
                    list_view.setAdapter(myRequestAdapter);
                }
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