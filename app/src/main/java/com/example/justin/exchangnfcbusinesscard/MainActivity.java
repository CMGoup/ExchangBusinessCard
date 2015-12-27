package com.example.justin.exchangnfcbusinesscard;

import android.content.ContentResolver;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
    {
        public Intent SelfIntent;

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

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            tesbtn=(Button)findViewById(R.id.tesbtn);
            tesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, 1);
                }
            });
        }


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
   /*         @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*****************交換名片********************/
        if (id == R.id.nav_camera) {
            NfcExchang(SelfIntent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_slideshow) {
           /* btn3=(Button)findViewById(R.id.nav_slideshow);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, 2);
                }
            });*/
        } else if (id == R.id.nav_manage) {
            /*btn4=(Button)findViewById(R.id.nav_gallery);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, 3);
                }
            });*/
        } else if (id == R.id.nav_share) {
           /* btn5=(Button)findViewById(R.id.nav_gallery);
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, 4);
                }
            });*/
        } else if (id == R.id.nav_send) {
           /* btn6=(Button)findViewById(R.id.nav_gallery);
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, 5);
                }
            });*/
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
                SelfIntent = data;
                setSelfCardView(SelfIntent);
            }
        }
    }
    private void ScalePic(Bitmap bitmap,int phone)
    {
        //縮放比例預設為1
        float mScale = 1 ;

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if(bitmap.getWidth() > phone )
        {
            //判斷縮放比例
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix() ;
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    mMat,
                    false);
            img.setImageBitmap(mScaleBitmap);
        }
        else img.setImageBitmap(bitmap);
    }

    /**************回傳個人資料顯示****************/
    private void setSelfCardView(Intent intent){

        Bundle b = intent.getExtras();

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


//        Uri uri = data.getData();
//        ContentResolver cr = this.getContentResolver();
//                try
//                {
//                    //讀取照片，型態為Bitmap
//                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//
//                    //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
//                    if(bitmap.getWidth()>bitmap.getHeight())ScalePic(bitmap,
//                            mPhone.heightPixels);
//                    else ScalePic(bitmap,mPhone.widthPixels);
//                }
//                catch (FileNotFoundException e)
//                {
//                }
    }

    private void NfcExchang(Intent intent){
        intent.setClass(MainActivity.this, NfcSend.class);
        startActivity(intent);
    }

}
