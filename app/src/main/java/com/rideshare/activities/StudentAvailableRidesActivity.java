package com.rideshare.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.adapters.CarDetailsAdapter;
import com.rideshare.adapters.StudentAvailableRidesAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.RidesListPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAvailableRidesActivity extends AppCompatActivity {

    List<RidesListPojo> ridesListPojo;
    StudentAvailableRidesAdapter studentAvailableRidesAdapter;
    ListView list_view;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_available_rides);

        getSupportActionBar().setTitle("Available Rides");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);

        ridesListPojo=new ArrayList<>();
        getAvailableRides();

    }
    public void getAvailableRides(){
        progressDialog = new ProgressDialog(StudentAvailableRidesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<RidesListPojo>> call = service.getAvailableRides();
        call.enqueue(new Callback<List<RidesListPojo>>() {
            @Override
            public void onResponse(Call<List<RidesListPojo>> call, Response<List<RidesListPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(StudentAvailableRidesActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    ridesListPojo=response.body();
                    studentAvailableRidesAdapter =new StudentAvailableRidesAdapter(ridesListPojo,StudentAvailableRidesActivity.this);
                    list_view.setAdapter(studentAvailableRidesAdapter);

                }
            }
            @Override
            public void onFailure(Call<List<RidesListPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(StudentAvailableRidesActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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