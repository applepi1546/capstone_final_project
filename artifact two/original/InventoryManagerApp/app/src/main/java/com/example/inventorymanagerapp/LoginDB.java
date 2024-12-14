package com.example.inventorymanagerapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDB extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "inventory_manager.db";
    private static final int DATABASE_VERSION = 6;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    public static String COLUMN_PHONE = "phone_number";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_PHONE + " TEXT" +
                    ")";

    public LoginDB(Context context) // Constructor
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)  // Create table
    {
        db.execSQL(CREATE_TABLE);
        db.execSQL(InventoryDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String username, String password) // Add user
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, "");
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) // Check user
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void deleteUser(String username) // Delete user
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
    }


    public void updatePhoneNumber(String username, String string)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, string);
        db.update(TABLE_NAME, values, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
    }

    @SuppressLint("Range")
    public String getPhoneNumber(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() < 0)
        {
            cursor.close();
            db.close();
            return "";
        }
        else{
            String phone;
            phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            cursor.close();
            db.close();
            return phone;
        }
    }
}
