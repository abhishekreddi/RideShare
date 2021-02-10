package com.rideshare.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class RideHistoryAdapter extends BaseAdapter {
    List<RidesListPojo> ar;
    Context cnt;

    public RideHistoryAdapter(List<RidesListPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.list_rider_history_list, null);

        TextView tv_vahicle = (TextView) obj2.findViewById(R.id.tv_vahicle);
        tv_vahicle.setText("Vehicle : " + ar.get(pos).getSeats());

        TextView tv_seats = (TextView) obj2.findViewById(R.id.tv_seats);
        tv_seats.setText("Seats : " + ar.get(pos).getSeats());

        TextView tv_price = (TextView) obj2.findViewById(R.id.tv_price);
        tv_price.setText("Price : " + ar.get(pos).getSeats());

        TextView tv_aavailable = (TextView) obj2.findViewById(R.id.tv_aavailable);
        tv_aavailable.setText("Available : " + ar.get(pos).getSeats());


        Button btn_edit = (Button) obj2.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cnt, RideConfirmationActivity.class);
                intent.putExtra("vehicle",ar.get(pos).getSeats());
                intent.putExtra("seats",ar.get(pos).getSeats());
                intent.putExtra("price",ar.get(pos).getSeats());
                intent.putExtra("available",ar.get(pos).getSeats());
                cnt.startActivity(intent);

            }
        });

        Button btn_delete = (Button) obj2.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(cnt);
                builder1.setMessage("Do you want to Delte.");  //message we want to show the end user
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); //cancle the alert dialog box
                                ((Activity)cnt).finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        return obj2;
    }
}