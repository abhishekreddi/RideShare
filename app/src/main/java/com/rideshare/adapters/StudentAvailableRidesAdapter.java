package com.rideshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.models.RidesListPojo;

import java.util.List;

public class StudentAvailableRidesAdapter extends BaseAdapter {
    List<RidesListPojo> availablerides;
    String URL="http://rideshare.live/rideshare/";
    Context cnt;

    public StudentAvailableRidesAdapter(List<RidesListPojo> availablerides, Context cnt) {
        this.availablerides = availablerides;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return availablerides.size();
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
        View obj2 = obj1.inflate(R.layout.list_student_available_ride, null);


        ImageView imageview=(ImageView)obj2.findViewById(R.id.imageview);
        Glide.with(cnt).load(URL+availablerides.get(pos).photo).into(imageview);

        TextView tv_vahicle = (TextView) obj2.findViewById(R.id.tv_vahicle);
        tv_vahicle.setText("Vehicle : " + availablerides.get(pos).getCname());

        TextView tv_from = (TextView) obj2.findViewById(R.id.tv_from);
        tv_from.setText("Source : " +  availablerides.get(pos).getSource());

        TextView tv_to = (TextView) obj2.findViewById(R.id.tv_to);
        tv_to.setText("Destination : " +  availablerides.get(pos).getSource());

        TextView tv_color = (TextView) obj2.findViewById(R.id.tv_color);
        tv_color.setText("Color : " +  availablerides.get(pos).getColor());

        TextView tv_numplate = (TextView) obj2.findViewById(R.id.tv_numplate);
        tv_numplate.setText("No.Plate : " +  availablerides.get(pos).getCplate());

        TextView tv_seats = (TextView) obj2.findViewById(R.id.tv_seats);
        tv_seats.setText("Seats : " +  availablerides.get(pos).getSeats());


        TextView tv_date = (TextView) obj2.findViewById(R.id.tv_date);
        tv_date.setText("Date : " +  availablerides.get(pos).getDat());

        TextView tv_time = (TextView) obj2.findViewById(R.id.tv_time);
        tv_time.setText("Time : " +  availablerides.get(pos).getTim());

        TextView tvPrice = (TextView) obj2.findViewById(R.id.tvPrice);
        tvPrice.setText("Price : " +  availablerides.get(pos).getRid());


        TextView tvStatus = (TextView) obj2.findViewById(R.id.tvStatus);
        tvStatus.setText("Status : " +  availablerides.get(pos).getCid());




        return obj2;
    }
}