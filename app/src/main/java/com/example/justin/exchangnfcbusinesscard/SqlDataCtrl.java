package com.example.justin.exchangnfcbusinesscard;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/**
 * Created by Justin on 2015/12/26.
 */
public class SqlDataCtrl {

    public static final String TABLE_NAME = "item";
    public static final String KEY_ID = "_id";

    /***************cell items**************/
    public static final String NAME_COLUMN = "name";
    public static final String JOB_COLUMN = "job";
    public static final String CELLPHINE_CILUMN = "cellphone";
    public static final String EMAIL_COLUMN = "email";
    public static final String COMPANY_COLUMN = "company";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    private SQLiteDatabase db;

    public SqlDataCtrl(Context context){
        db = MyDBHelper.getDatabase(context);
    }

    /******************關閉資料庫*****************/
    public void close() {
        db.close();
    }

    /*****************插入項目********************/
    public ClipData.Item insert(ClipData.Item item){
        ContentValues cv = new ContentValues();
        Bundle b = item.getIntent().getExtras();
        cv.put(NAME_COLUMN, b.getString("REQ1"));
        cv.put(JOB_COLUMN, b.getString("REQ2"));
        cv.put(CELLPHINE_CILUMN, b.getString("REQ3"));
        cv.put(EMAIL_COLUMN, b.getString("REQ4"));
        cv.put(COMPANY_COLUMN, b.getString("REQ5"));
        cv.put(PHONE, b.getString("REQ6"));
        cv.put(ADDRESS, b.getString("REQ7"));
        return item;
    }

}
