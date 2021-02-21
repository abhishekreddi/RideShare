package com.rideshare.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rideshare.R;
import com.rideshare.adapters.messagesadapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ResponseData;
import com.rideshare.models.msgs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class messagingactivity extends AppCompatActivity {
ApiService apiService;
String frm;
String eto;
String pid="3";
List<msgs> msg=new ArrayList<msgs>();

EditText msgtext;
    ProgressDialog pd;
    messagesadapter messagesadapter;
Button send;
    Runnable r;
     RecyclerView recyclerView;
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagingactivity);
//        getSupportActionBar().setTitle("Chat");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        msgtext=(EditText)findViewById(R.id.msgtext);
        send=(Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(msgtext.getText().toString().isEmpty())
                {
                    Toast.makeText(messagingactivity.this,"Please enter message", Toast.LENGTH_SHORT).show();
                }
                sendMessage(getIntent().getStringExtra("from"),getIntent().getStringExtra("to"),getIntent().getStringExtra("rid"));
            }
        });

//        Toast.makeText(messagingactivity.this,getIntent().getStringExtra("pid").toString()+getIntent().getStringExtra("from").toString()+getIntent().getStringExtra("to").toString(), Toast.LENGTH_SHORT).show();
        frm=getIntent().getStringExtra("from");
        eto=getIntent().getStringExtra("to");
        Log.d("checktool",frm+""+eto);
        recyclerView=findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        pd = new ProgressDialog(getApplicationContext());
//        pd.setTitle("Please wait,Data is being loading.");
//        pd.show();


}