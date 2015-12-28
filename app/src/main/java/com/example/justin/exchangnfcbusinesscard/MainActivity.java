package com.example.justin.exchangnfcbusinesscard;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
    {
        public Bundle SelfBundle;
        public SqlDataCtrl SDL;

        public static final long FRIST_ID = 1;

        SharedPreferences pre;
        SharedPreferences.Editor preEdit;
        ImageView img;
        TextView name, job, cellphone, email, company, phone, address;
        Button btn1, btn2, btn3, btn4, btn5, btn6,tesbtn;
        private DisplayMetrics mPhone;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            /****************DataBase setting*****************/
            SDL = new SqlDataCtrl(getApplicationContext());

            if(SDL.getCount() > 0){
                SelfBundle = SDL.getData(FRIST_ID);
                this.setSelfCardView(SelfBundle);
            }else{
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("尚未設定個人資料")
                        .setMessage("請按下確定鍵開始設定屬於自己的電子名片")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, Main2Activity.class);
                                startActivityForResult(intent, 1);
                            }
                        }).show();
            }
            /******************Set Veiw Button***************/
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        SDL.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*****************交換名片********************/
        if (id == R.id.nav_camera) {
            SelfBundle.putLong("id", FRIST_ID);
            NfcExchang(SelfBundle);

        /****************設定個人資料***************/
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_slideshow) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == 101) {
                //在TextView顯示對應資料
            }
        }
        if (requestCode == 1) {
            if (resultCode == 1010) {
                SelfBundle = data.getExtras();
                SelfBundle.putLong("id", FRIST_ID);
                setSelfCardView(SelfBundle);
                if(SDL.getCount()>0){
                    SDL.update(SelfBundle);
                }else{
                    SDL.insert(SelfBundle);
                }

                Toast.makeText(this, "以更新個人資料", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**************回傳個人資料顯示****************/
    private void setSelfCardView(Bundle b){

        img=(ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.Name);
        job = (TextView) findViewById(R.id.Job);
        cellphone = (TextView) findViewById(R.id.Cellphone);
        email = (TextView) findViewById(R.id.Email);
        company = (TextView) findViewById(R.id.Company);
        phone = (TextView) findViewById(R.id.Phone);
        address = (TextView) findViewById(R.id.Address);

        name.setText(b.getString("REQ1"));
        job.setText(b.getString("REQ2"));
        cellphone.setText(b.getString("REQ3"));
        email.setText(b.getString("REQ4"));
        company.setText(b.getString("REQ5"));
        phone.setText(b.getString("REQ6"));
        address.setText(b.getString("REQ7"));
        /*************轉換並顯示圖片*****************/
        Bitmap bitmap = BitmapFactory.decodeByteArray(b.getByteArray("image"), 0, b.getByteArray("image").length);
        img.setImageBitmap(bitmap);
        Log.d("SQL", bitmap.toString());

    }

    private void NfcExchang(Bundle b){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, NfcSend.class);
        intent.putExtras(b);
        startActivity(intent);
    }

}
