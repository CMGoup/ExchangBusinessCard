package com.example.justin.exchangnfcbusinesscard;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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

    /***********SQL 創建表格指令*************/
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID +            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN +       " TEXT NOT NULL, " +
                    JOB_COLUMN +        " TEXT NOT NULL, " +
                    CELLPHONE_CILUMN +  " INTEGER NOT NULL, " +
                    EMAIL_COLUMN +      " TEXT NOT NULL, " +
                    COMPANY_COLUMN +    " TEXT NOT NULL, " +
                    PHONE_COLUMN +      " INTEGER NOT NULL, " +
                    ADDRESS_COLUMN +    " TEXT NOT NULL)";

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

        b.putLong("id", db.insert(TABLE_NAME, null, cv));
        return b;
    }

}
