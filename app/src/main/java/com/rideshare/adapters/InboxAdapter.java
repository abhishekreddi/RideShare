package com.rideshare.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.activities.InboxActivity;
import com.rideshare.activities.MyPostRidesAActivity;
import com.rideshare.activities.StudentRideDetailsActivity;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.InboxPojo;
import com.rideshare.models.ResponseData;
import com.rideshare.models.RidesListPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxAdapter extends BaseAdapter {
    List<InboxPojo> inboxPojos;
    String URL="http://rideshare.live/rideshare/";
    Context cnt;

    public InboxAdapter(List<InboxPojo> inboxpojo, Context cnt) {
        this.inboxPojos = inboxpojo;
        this.cnt = cnt;

    }

    @Override
    public int getCount() {
        return inboxPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_inbox, null);


       TextView tvFrom = (TextView) obj2.findViewById(R.id.tvFrom);
        tvFrom.setText("From : " + inboxPojos.get(pos).getSource());

        TextView tvTo = (TextView) obj2.findViewById(R.id.tvTo);
        tvTo.setText("To : " +  inboxPojos.get(pos).getDestination());

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " +  inboxPojos.get(pos).getDat());

        TextView tvTime = (TextView) obj2.findViewById(R.id.tvTime);
        tvTime.setText("Time : " +  inboxPojos.get(pos).getTim());

        TextView tvAmount = (TextView) obj2.findViewById(R.id.tvAmount);
        tvAmount.setText("Amount : " +  inboxPojos.get(pos).getAmount()+"$");

       

        Button btnConfirmed=(Button)obj2.findViewById(R.id.btnConfirmed);
        btnConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRideStatus(inboxPojos.get(pos).getBid(),"Confirmed");


            }
        });

        Button btnReject=(Button)obj2.findViewById(R.id.btnReject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRideStatus(inboxPojos.get(pos).getBid(),"Reject");


            }
        });

        return obj2;
    }
    ProgressDialog progressDialog;
    public void updateRideStatus(String ID,String Status) {
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.updaterequest(ID,Status);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body() == null) {
                    Toast.makeText(cnt, "Server issue", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(cnt, InboxActivity.class);
                    cnt.startActivity(intent);
                    ((Activity) cnt).finish();
                    Toast.makeText(cnt, " Status Updated successfully", Toast.LENGTH_SHORT).show();

                }
            }

           
        });
    }

}