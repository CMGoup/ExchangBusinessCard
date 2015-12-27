package com.example.justin.exchangnfcbusinesscard;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class NfcSend extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback,
        NfcAdapter.OnNdefPushCompleteCallback {

    String ReMsg;
    NfcAdapter mNfcAdapter;     //Nfc宣告
    TextView textView;
    private static final int MESSAGE_SENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_send);

        textView = (TextView)findViewById(R.id.textView);
        Bundle b = getIntent().getExtras();
        if(b != null){
            ReMsg =   b.getString("REQ1") + "\n"
                    + b.getString("REQ2") + "\n"
                    + b.getString("REQ3") + "\n"
                    + b.getString("REQ4") + "\n"
                    + b.getString("REQ5") + "\n"
                    + b.getString("REQ6") + "\n"
                    + b.getString("REQ7");
        }


        /*************Check for available NFC Adapter**************/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter == null){
            textView.setText("NOT Support NFC!");
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }else{
            textView.setText("Support NFC!");
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
        textView.setText("NFC message:\n" + new String(msg.getRecords()[0].getPayload()));
    }
}
