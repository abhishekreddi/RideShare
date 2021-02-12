package com.rideshare.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.ResponseData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashBoardActivity extends AppCompatActivity {

    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    CardView cd_search_ride, cd_post_ride, cdAvailableRides;
    EditText et_no_of_seats, et_amount;
    Button btnDriver, btnStudent;
    RelativeLayout studentLayout;
    LinearLayout addTrip;
    String session, carId;
    Spinner spinTypeofVehicle, spinFrom, SpinTo;
    SharedPreferences sharedPreferences;
    Button btn_post;
    TextView tv_date, tv_time;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        navigationView();
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def_val");


        btnDriver = (Button) findViewById(R.id.btnDriver);
        btnStudent = (Button) findViewById(R.id.btnStudent);

        studentLayout = (RelativeLayout) findViewById(R.id.studentLayout);
        spinTypeofVehicle = (Spinner) findViewById(R.id.spinTypeofVehicle);
        spinFrom = (Spinner) findViewById(R.id.spinFrom);
        SpinTo = (Spinner) findViewById(R.id.SpinTo);
        et_no_of_seats = (EditText) findViewById(R.id.et_no_of_seats);
        et_amount = (EditText) findViewById(R.id.et_amount);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmTimePicker();
            }
        });

        getCarNames();


        btnStudent.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change color
                    btnStudent.getBackground().setColorFilter(new LightingColorFilter(0x000, 0xFFAA0000));


                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // set to normal color

                    btnStudent.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                    startActivity(new Intent(UserDashBoardActivity.this,StudentDashboardActivity.class));


                }
                return true;
            }
        });
        btnDriver.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change color
                    btnDriver.getBackground().setColorFilter(new LightingColorFilter(0x000, 0xFFAA0000));

                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // set to normal color
                    btnDriver.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));

                }

                return true;
            }
        });

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashBoardActivity.this, UserDashBoardActivity.class));
                finish();

            }
        });

        btn_post = (Button) findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostaRide();
            }
        });
    }

    ProgressDialog progressDialog;

    private void PostaRide() {
        String from = spinFrom.getSelectedItem().toString();
        String to = SpinTo.getSelectedItem().toString();
        String noofseats = et_no_of_seats.getText().toString();
        String date = tv_date.getText().toString();
        String time = tv_time.getText().toString();
        String amount = et_amount.getText().toString();

        progressDialog = new ProgressDialog(UserDashBoardActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.postaride(from, to, noofseats, date, time, amount, session);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(UserDashBoardActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserDashBoardActivity.this, UserDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(UserDashBoardActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserDashBoardActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void navigationView() {
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.myrides:
                        Intent intent = new Intent(getApplicationContext(), RidesListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.ride_history:
                        Intent intent1 = new Intent(getApplicationContext(), RideHistoryActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.add_car:
                        Intent add_car = new Intent(getApplicationContext(), CardetailsActivity.class);
                        startActivity(add_car);
                        break;


                    case R.id.edit_profile:
                        Intent view_jobs = new Intent(getApplicationContext(), EditProfileActivity.class);
                        startActivity(view_jobs);
                        break;

                    case R.id.logout:
                        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<String> cid;

    private void getCarNames() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CarDetailsPojo>> call = apiService.getmycars(session);
        call.enqueue(new Callback<List<CarDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<CarDetailsPojo>> call, Response<List<CarDetailsPojo>> response) {
                if (response.isSuccessful()) {
                    final List<CarDetailsPojo> carname = response.body();
                    //Toast.makeText(UserDashBoardActivity.this, ""+carname.size(), Toast.LENGTH_SHORT).show();
                    if (carname != null) {
                        if (carname.size() > 0) {
                            ArrayList<String> cname = new ArrayList<String>();
                            cid = new ArrayList<String>();
                            cname.add("Select Car");
                            cid.add("");
                            for (int i = 0; i < carname.size(); i++) {
                                cname.add(carname.get(i).getCname());
                                cid.add(carname.get(i).getCid());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(UserDashBoardActivity.this, android.R.layout.simple_spinner_dropdown_item, cname);
                            spinTypeofVehicle.setAdapter(adp);
                            spinTypeofVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(UserDashBoardActivity.this, "" + cid.get(spinTypeofVehicle.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();

                                    carId = cid.get(spinTypeofVehicle.getSelectedItemPosition());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CarDetailsPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
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

    public void setmTimePicker() {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(UserDashBoardActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}