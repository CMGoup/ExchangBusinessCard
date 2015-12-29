package com.example.justin.exchangnfcbusinesscard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class NfcSend extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback,
        NfcAdapter.OnNdefPushCompleteCallback {

    String ReMsg;
    NfcAdapter mNfcAdapter;     //Nfc宣告
    TextView textView;
    String ReceiveStr;
    public Bundle bundle;
    private static final int MESSAGE_SENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_send);

        textView = (TextView)findViewById(R.id.textView);
        Bundle b = getIntent().getExtras();
//        String picture = Base64.encodeToString(b.getByteArray("image"), Base64.DEFAULT);
//        Log.d("SQL", b.getByteArray("image").toString());
        if(b != null){
            ReMsg =   b.getString("REQ1") + "\n"
                    + b.getString("REQ2") + "\n"
                    + b.getString("REQ3") + "\n"
                    + b.getString("REQ4") + "\n"
                    + b.getString("REQ5") + "\n"
                    + b.getString("REQ6") + "\n"
                    + b.getString("REQ7") + "\n"
                    + "pivture";
        }


        /*************Check for available NFC Adapter**************/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter == null){
            textView.setText("本裝置不支援NFC");
            new AlertDialog.Builder(NfcSend.this)
                    .setTitle("本裝置不支援NFC")
                    .setMessage("點擊離開鍵返回個人名片")
                    .setPositiveButton("離開", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }else{
            textView.setText("將兩支手機背靠背後點擊後交換名片~");
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //String text = ("Beam me up, Android!\n" + "Beam Time: " + System.currentTimeMillis());
        NdefMessage msg = new NdefMessage(new NdefRecord[]{
                NdefRecord.createMime("application/vnd.com.example.justin.exchangnfcbusinesscard"
                        , ReMsg.getBytes(Charset.forName("UTF-8")))
        });
        return msg;
    }

    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
        //finish();
    }

    /** This handler receives a message from onNdefPushComplete */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        ReceiveStr = new String(msg.getRecords()[0].getPayload());
        setView(this.DatatoBundle(ReceiveStr));
        //textView.setText("NFC message:\n" + ReceiveStr);

        /***************儲存NFC名片*******************/
        Button btn = (Button) findViewById(R.id.nfc_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SQL", "Data is is " + String.valueOf(MainActivity.SDL.insert(bundle)));
                Toast.makeText(getApplicationContext(), "已存入名片匣", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void setView(Bundle b){
        TextView name = (TextView)findViewById(R.id.nfc_name);
        TextView job = (TextView)findViewById(R.id.nfc_job);
        TextView cellphone = (TextView)findViewById(R.id.nfc_cellphone);
        TextView email = (TextView)findViewById(R.id.nfc_email);
        TextView company = (TextView)findViewById(R.id.nfc_company);
        TextView phone = (TextView)findViewById(R.id.nfc_phone);
        TextView address = (TextView)findViewById(R.id.nfc_address);
        ImageView image = (ImageView)findViewById(R.id.nfc_image);

        name.setText(b.getString("REQ1"));
        job.setText(b.getString("REQ2"));
        cellphone.setText(b.getString("REQ3"));
        email.setText(b.getString("REQ4"));
        company.setText(b.getString("REQ5"));
        phone.setText(b.getString("REQ6"));
        address.setText(b.getString("REQ7"));

    }
    public Bundle DatatoBundle(String str){
        bundle = new Bundle();
        String[] stringSplit = str.split("\n");

        bundle.putString("REQ1", stringSplit[0]);
        bundle.putString("REQ2", stringSplit[1]);
        bundle.putString("REQ3", stringSplit[2]);
        bundle.putString("REQ4", stringSplit[3]);
        bundle.putString("REQ5", stringSplit[4]);
        bundle.putString("REQ6", stringSplit[5]);
        bundle.putString("REQ7", stringSplit[6]);

        byte[] theByteArray = stringSplit[7].getBytes();
        bundle.putByteArray("image", theByteArray);

        return bundle;
    }

}
