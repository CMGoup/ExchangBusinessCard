package com.example.justin.exchangnfcbusinesscard;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.awt.font.TextAttribute;

/**
 * Created by Justin on 2015/12/26.
 */
public class SqlDataCtrl {

    public static final String TABLE_NAME = "item";
    public static final String KEY_ID = "_id";

    /***************cell items**************/
    public static final String NAME_COLUMN = "name";
    public static final String JOB_COLUMN = "job";
    public static final String CELLPHONE_CILUMN = "cellphone";
    public static final String EMAIL_COLUMN = "email";
    public static final String COMPANY_COLUMN = "company";
    public static final String PHONE_COLUMN = "phone";
    public static final String ADDRESS_COLUMN = "address";
    public static final String IMAGE_COLUMN = "image";

    /***********SQL 創建表格指令*************/
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID +            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN +       " TEXT, " +
                    JOB_COLUMN +        " TEXT, " +
                    CELLPHONE_CILUMN +  " TEXT, " +
                    EMAIL_COLUMN +      " TEXT, " +
                    COMPANY_COLUMN +    " TEXT, " +
                    PHONE_COLUMN +      " TEXT, " +
                    ADDRESS_COLUMN +    " TEXT, " +
                    IMAGE_COLUMN +      " TEXT)";

    private SQLiteDatabase db;

    public SqlDataCtrl(Context context){

        db = MyDBHelper.getDatabase(context);
    }

    /******************關閉資料庫*****************/
    public void close() {
        db.close();
    }

    /***************插入項目回傳id******************/
    public Bundle insert(Bundle b){
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, b.getString("REQ1"));
        cv.put(JOB_COLUMN, b.getString("REQ2"));
        cv.put(CELLPHONE_CILUMN, b.getString("REQ3"));
        cv.put(EMAIL_COLUMN, b.getString("REQ4"));
        cv.put(COMPANY_COLUMN, b.getString("REQ5"));
        cv.put(PHONE_COLUMN, b.getString("REQ6"));
        cv.put(ADDRESS_COLUMN, b.getString("REQ7"));
        cv.put(IMAGE_COLUMN, Base64.encodeToString(b.getByteArray("image"), Base64.DEFAULT));

        b.putLong("id", db.insert(TABLE_NAME, null, cv));
        return b;
    }

    /******************更新欄位*****************/
    public boolean update(Bundle b){
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, b.getString("REQ1"));
        cv.put(JOB_COLUMN, b.getString("REQ2"));
        cv.put(CELLPHONE_CILUMN, b.getString("REQ3"));
        cv.put(EMAIL_COLUMN, b.getString("REQ4"));
        cv.put(COMPANY_COLUMN, b.getString("REQ5"));
        cv.put(PHONE_COLUMN, b.getString("REQ6"));
        cv.put(ADDRESS_COLUMN, b.getString("REQ7"));
        cv.put(IMAGE_COLUMN, Base64.encodeToString(b.getByteArray("image"), Base64.DEFAULT));
        String where = KEY_ID + "=" + b.getLong("id");
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    /****************取得id欄位******************/
    public Bundle getData(long id){
        Bundle b = new Bundle();
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);
        if(result.moveToFirst()) {
            b.putString("REQ1", result.getString(1));
            b.putString("REQ2", result.getString(2));
            b.putString("REQ3", result.getString(3));
            b.putString("REQ4", result.getString(4));
            b.putString("REQ5", result.getString(5));
            b.putString("REQ6", result.getString(6));
            b.putString("REQ7", result.getString(7));

            byte[] bytes = Base64.decode(result.getString(8), Base64.DEFAULT);
            b.putByteArray("image", bytes);
        }
        result.close();
        return b;
    }
    /***************取得資料數量***************/
    public int getCount(){
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    /*************id欄位是否存在***************/
//    public boolean isExist(long id){
//        Bundle b;
//        String where = KEY_ID + "=" + id;
//        Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);
//        result.isNull()
//        return true;
//    }
}
