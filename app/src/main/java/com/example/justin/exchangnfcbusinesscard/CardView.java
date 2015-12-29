package com.example.justin.exchangnfcbusinesscard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.Policy;

public class CardView extends AppCompatActivity {
    public int viewid = 2;
    private TextView name, job, cellphone, email, company, phone, address;
    private Button previous, next;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        previous = (Button)findViewById(R.id.prebtn);
        next = (Button)findViewById(R.id.nextbtn);
        final int count = MainActivity.SDL.getCount();
        Log.d("SQL", "Database amoumt of data is " + String.valueOf(count));
        if(count == 1){
            new AlertDialog.Builder(CardView.this)
                    .setTitle("尚未加入任何名片")
                    .setMessage("請按下確定鍵返回")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }else{
            setCardView(viewid);
        }
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewid--;

                if(viewid == 1){
                    viewid = count;
                }
                setCardView(viewid);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewid++;
                if(viewid == count + 1){
                    viewid = 2;
                }
                setCardView(viewid);
            }
        });
    }
    public void setCardView(long id){
        Bundle b = MainActivity.SDL.getData(id);
        name = (TextView)findViewById(R.id.card_name);
        job = (TextView)findViewById(R.id.card_job);
        cellphone = (TextView)findViewById(R.id.card_cellphone);
        email = (TextView)findViewById(R.id.card_email);
        company = (TextView)findViewById(R.id.card_company);
        phone = (TextView)findViewById(R.id.card_phone);
        address = (TextView)findViewById(R.id.card_address);

        layout = (LinearLayout)findViewById(R.id.card_background);

        switch ((int)id % 5){
            case 0:
                layout.setBackgroundColor(Color.rgb(102, 153, 0));
                break;
            case 1 :
                layout.setBackgroundColor(Color.rgb(255, 136, 0));
                break;
            case 2 :
                layout.setBackgroundColor(Color.rgb(0, 153, 204));
                break;
            case 3 :
                layout.setBackgroundColor(Color.rgb(204, 136, 0));
                break;
            case 4 :
                layout.setBackgroundColor(Color.BLACK);
                break;
        }
        name.setText(b.getString("REQ1"));
        job.setText(b.getString("REQ2"));
        cellphone.setText(b.getString("REQ3"));
        email.setText(b.getString("REQ4"));
        company.setText(b.getString("REQ5"));
        phone.setText(b.getString("REQ6"));
        address.setText(b.getString("REQ7"));
    }
}
