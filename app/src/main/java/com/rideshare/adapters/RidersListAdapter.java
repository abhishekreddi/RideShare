package com.rideshare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rideshare.R;
import com.rideshare.activities.RideConfirmationActivity;
import com.rideshare.models.RidesListPojo;

import java.util.List;

public class RidersListAdapter extends BaseAdapter {
    List<RidesListPojo> ar;
    Context cnt;

    public RidersListAdapter(List<RidesListPojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
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
        View obj2 = obj1.inflate(R.layout.list_riders_list, null);

        TextView tv_vahicle = (TextView) obj2.findViewById(R.id.tv_vahicle);
        tv_vahicle.setText("Vehicle : " + ar.get(pos).getSeats());

        TextView tv_seats = (TextView) obj2.findViewById(R.id.tv_seats);
        tv_seats.setText("Seats : " + ar.get(pos).getSeats());

        TextView tv_price = (TextView) obj2.findViewById(R.id.tv_price);
        tv_price.setText("Price : " + ar.get(pos).getCplate());

        TextView tv_aavailable = (TextView) obj2.findViewById(R.id.tv_aavailable);
        tv_aavailable.setText("Available : " + ar.get(pos).getDat());


        Button btn_book = (Button) obj2.findViewById(R.id.btn_book);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(cnt, RideConfirmationActivity.class);
                intent.putExtra("vehicle",ar.get(pos).getDat());
                intent.putExtra("seats",ar.get(pos).getSeats());
                intent.putExtra("price",ar.get(pos).getDat());
                intent.putExtra("available",ar.get(pos).getDat());
                cnt.startActivity(intent);

            }
        });

        return obj2;
    }
}