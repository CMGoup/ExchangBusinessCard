package com.example.justin.exchangnfcbusinesscard;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class Main2Activity extends AppCompatActivity {
    private DisplayMetrics mPhone;
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;

    ImageButton btn;
    Button savebtn;
    SharedPreferences pre;
    SharedPreferences.Editor preEdit;
    EditText name,job,cellphone,email,company,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);

            btn = (ImageButton) findViewById(R.id.imageButton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("選擇頭像");
                    builder.setMessage("請選擇來源");
                    builder.setPositiveButton("照相", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialog,int which){//到照相畫面
                                Toast.makeText(getApplicationContext(), "照相", Toast.LENGTH_SHORT).show();
                                /*開啟相機功能，並將拍照後的圖片存入SD卡相片集內，須由startActivityForResult且
                                帶入 requestCode進行呼叫，原因為拍照完畢後返回程式後則呼叫onActivityResult*/
                                ContentValues value = new ContentValues();
                                value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        value);
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri.getPath());
                                startActivityForResult(intent, CAMERA);
                            /*private Camera.PictureCallback jpeg = new Camera.PictureCallback() {//執行照相功能，傳入JPEG資料
                                public void onPictureTaken(byte[] bytes, Camera camera) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    try {//設定要儲存的圖檔位置與名稱
                                        File imgFile = new File("/sdcard/myCamera_" + new java.util.Data().getTime + ".jpg");
                                        //設定輸出資料流
                                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(imgFile));
                                        //設定檔案格式與圖檔品質
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        //寫出檔案
                                        out.flush();
                                        out.close();
                                        out = null;
                                        //進入預覽
                                        bfCamera.startPreview();
                                    } catch (Exception e) {
                                        Log.e("camera", "take photo error:" + e);
                                    }
                                }
                            };*/
                            }
                        }

                    );
                    builder.setNegativeButton("相簿", new DialogInterface.OnClickListener()

                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//到相簿畫面
                                    Toast.makeText(getApplicationContext(), "相簿", Toast.LENGTH_SHORT).show();
                                    //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//設定使用全螢幕顯示，要置於setContentView之前
                                    /*開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因
                                    為點選相片後返回程式呼叫onActivityResult*/
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intent, PHOTO);
                                }
                            }

                    );
                    builder.show();
                }
            });
            /*************Image Button 縮放*************/
            


            savebtn = (Button) findViewById(R.id.Savebtn);
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = (EditText) findViewById(R.id.editText1);
                    job = (EditText) findViewById(R.id.editText2);
                    cellphone = (EditText) findViewById(R.id.editText3);
                    email = (EditText) findViewById(R.id.editText4);
                    company = (EditText) findViewById(R.id.editText5);
                    phone = (EditText) findViewById(R.id.editText6);
                    address = (EditText) findViewById(R.id.editText7);

                    Bundle b = new Bundle();
                    b.putString("REQ1", name.getText().toString());
                    b.putString("REQ2", job.getText().toString());
                    b.putString("REQ3", cellphone.getText().toString());
                    b.putString("REQ4", email.getText().toString());
                    b.putString("REQ5", company.getText().toString());
                    b.putString("REQ6", phone.getText().toString());
                    b.putString("REQ7", address.getText().toString());

                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                    intent.putExtras(b);
                    setResult(1010, intent);
                    Main2Activity.this.finish();


                    // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    //fab.setOnClickListener(new View.OnClickListener() {
                    // @Override
                    //public void onClick(View v) {
                    // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //   .setAction("Action", null).show();
                }
            });
    }
    //拍照完畢或選取圖片後呼叫此函式
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ((requestCode == CAMERA || requestCode == PHOTO ) && data != null)
        {
            //取得照片路徑uri
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();

            try
            {
                //讀取照片，型態為Bitmap
                BitmapFactory.Options mOptions=new BitmapFactory.Options();
                mOptions.inSampleSize=2;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOptions);

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight())ScalePic(bitmap,
                        mPhone.heightPixels);
                else ScalePic(bitmap,mPhone.widthPixels);
            }
            catch (FileNotFoundException e)
            {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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
            btn.setImageBitmap(mScaleBitmap);
        }
        else btn.setImageBitmap(bitmap);
    }
}
