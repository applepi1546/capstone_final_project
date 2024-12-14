package com.example.inventorymanagerapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class InventoryDB extends SQLiteOpenHelper  // Inventory Database
{
    private static final String DATABASE_NAME = "inventory_manager.db";
    private static final int DATABASE_VERSION = 6;
    private static final String TABLE_NAME = "inventory"; // inventory table
    public static final String COLUMN_ID = "id";
    private static final String COLUMN_USER = "user";
    public static String COLUMN_ITEM_NAME = "item_name";
    public static String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_USERNAME = "username";
    static final String CREATE_TABLE = // Create table
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USER + " TEXT," +
                    COLUMN_ITEM_NAME + " TEXT," +
                    COLUMN_QUANTITY + " INTEGER," +
                    "FOREIGN KEY (" + COLUMN_USER + ") REFERENCES users(" + COLUMN_USERNAME + ")" +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }
    public InventoryDB(Context context) // Constructor
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) // when changing the database version
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(String user,String itemName ,int quantity) // add item
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, user);
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_QUANTITY, quantity);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllItemsForUser(String user) //fetch all items for a user
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_USER + " = ?", new String[]{user}, null, null, null);
    }

    public void updateItem(int id, String user, String itemName, int quantity) // update item
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, user);
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_QUANTITY, quantity);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteItem(int id) // delete item
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getInventory(String username) // get inventory
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER + " = ?", new String[]{username});
    }

    public void deleteInventory(String username) // delete inventory
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USER + " = ?", new String[]{username});
        db.close();
    }

}
