package com.rideshare.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.activities.StudentRideDetailsActivity;
import com.rideshare.models.SearchDetailsPojo;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    List<SearchDetailsPojo> searchDetails;
    Context cnt;
    String URL="http://rideshare.live/rideshare/";

    public SearchAdapter(List<SearchDetailsPojo> searchDetails, Context cnt) {
        this.searchDetails = searchDetails;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return searchDetails.size();
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
        View obj2 = obj1.inflate(R.layout.adapter_search_ride, null);

        ImageView carImage=(ImageView)obj2.findViewById(R.id.carImage);
        Glide.with(cnt).load(URL+searchDetails.get(pos).photo).into(carImage);

        TextView tvFrom = (TextView) obj2.findViewById(R.id.tvFrom);
        tvFrom.setText("From : " + searchDetails.get(pos).getSource());

        TextView tvTo = (TextView) obj2.findViewById(R.id.tvTo);
        tvTo.setText("To : " +  searchDetails.get(pos).getDestination());

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " +  searchDetails.get(pos).getDat()+"AM");


        TextView tvTime = (TextView) obj2.findViewById(R.id.tvTime);
        tvTime.setText("Time : " +  searchDetails.get(pos).getTim());

        Button btnBook=(Button)obj2.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cnt, StudentRideDetailsActivity.class);
                intent.putExtra("from",searchDetails.get(pos).getSource());
                intent.putExtra("to",searchDetails.get(pos).getDestination());
                intent.putExtra("date",searchDetails.get(pos).getDat());
                intent.putExtra("carname",searchDetails.get(pos).getCname());
                intent.putExtra("carcolor",searchDetails.get(pos).getColor());
                intent.putExtra("carnumplate",searchDetails.get(pos).getCplate());
                intent.putExtra("seats",searchDetails.get(pos).getSeats());
                intent.putExtra("time",searchDetails.get(pos).getTim());
                intent.putExtra("rid",searchDetails.get(pos).getRid());
                intent.putExtra("uname",searchDetails.get(pos).getEmail());
                cnt.startActivity(intent);
                ((Activity)cnt).finish();

            }
        });




        return obj2;
    }


}