package com.example.androidapplications;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

// 1. Subclassing SQLiteOpenHelper class
public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Messages.DB";
    public static final String TABLE_Of_My_ITEMS = "tableOfMyItems";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "messageName";
    private static final int VERSION_NUM = 1;

    // 2. Table/Database creation statement
    private static final String DATABASE_CREATE = "create table " + TABLE_Of_My_ITEMS + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";

    // 3. super constructor call
    public ChatDatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Of_My_ITEMS);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion = "  + oldVersion + " newVersion = " + newVersion);
    }

}
