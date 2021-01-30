package com.rideshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rideshare.R;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ResponseData;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostaRideActivity extends AppCompatActivity {
    EditText et_from,et_to,et_type_of_vehicle,et_no_of_seats,et_amount,et_vehicle_id;
    TextView tv_date;
    Button btn_post;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posta_ride);

        getSupportActionBar().setTitle("Post a ride");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_from=(EditText)findViewById(R.id.et_from);
        et_to=(EditText)findViewById(R.id.et_to);
        et_type_of_vehicle=(EditText)findViewById(R.id.et_type_of_vehicle);
        et_no_of_seats=(EditText)findViewById(R.id.et_no_of_seats);
        et_amount=(EditText)findViewById(R.id.et_amount);
        et_vehicle_id=(EditText)findViewById(R.id.et_vehicle_id);

        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    ProgressDialog progressDialog;
    private void submitData () {
        String from = et_from.getText().toString();
        String to = et_to.getText().toString();
        String typeofvehicle = et_type_of_vehicle.getText().toString();
        String noofseats = et_no_of_seats.getText().toString();
        String amount = et_amount.getText().toString();
        String vehicleID = et_vehicle_id.getText().toString();

        progressDialog = new ProgressDialog(PostaRideActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.postaride(from,to,typeofvehicle,noofseats,amount,vehicleID);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(PostaRideActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(PostaRideActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PostaRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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