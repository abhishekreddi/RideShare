package com.rideshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.rideshare.R;
import com.rideshare.adapters.RidersListAdapter;
import com.rideshare.models.RidesListPojo;

import java.util.ArrayList;
import java.util.List;

public class RidesListActivity extends AppCompatActivity {

    List<RidesListPojo> ridesListPojo;
    RidersListAdapter ridersListAdapter;
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);


        getSupportActionBar().setTitle("Riders List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);

        //DATABASE CONNECTION
/*
        ridesListPojo=new ArrayList<>();
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Available"));
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Available"));
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Available"));
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Not Available"));
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Available"));
        ridesListPojo.add(new RidesListPojo("ABC","3","100","Available"));  */

        ridersListAdapter=new RidersListAdapter(ridesListPojo,this);
        list_view.setAdapter(ridersListAdapter);

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