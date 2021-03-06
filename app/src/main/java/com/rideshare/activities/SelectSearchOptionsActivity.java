package com.rideshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rideshare.R;

import java.util.Calendar;

public class SelectSearchOptionsActivity extends AppCompatActivity {
    Spinner et_to, et_from;
    TextView tvDate;
    Button btn_search;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_search_options);

        getSupportActionBar().setTitle("Search Ride");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_to = (Spinner) findViewById(R.id.et_to);
        et_from = (Spinner) findViewById(R.id.et_from);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_from.getSelectedItem().toString().equals("Select From")) {
                    Toast.makeText(SelectSearchOptionsActivity.this, "Please Select From location", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_to.getSelectedItem().toString().equals("Choose To")) {
                    Toast.makeText(SelectSearchOptionsActivity.this, "Please enter To location", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (et_to.getSelectedItem().toString().equals(et_from.getSelectedItem())) {
                    Toast.makeText(SelectSearchOptionsActivity.this, "From and to should not be the same", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(SelectSearchOptionsActivity.this, SearchrideActivity.class);
                intent.putExtra("from", et_from.getSelectedItem().toString());
                intent.putExtra("to", et_to.getSelectedItem().toString());
                intent.putExtra("date", tvDate.getText().toString());
                startActivity(intent);
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

                        tvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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