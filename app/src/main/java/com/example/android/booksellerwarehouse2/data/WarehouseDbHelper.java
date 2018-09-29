package com.example.android.booksellerwarehouse2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.booksellerwarehouse2.data.BookWarehouseContract.WarehouseEntry;

/**
 * Database helper for Books app. Manages database creation and version management.
 */
public class WarehouseDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = WarehouseDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "bookstore.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link WarehouseDbHelper}.
     *
     * @param context of the app
     */
    public WarehouseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the books table
        String SQL_CREATE_BOOKSTORE = "CREATE TABLE " + WarehouseEntry.TABLE_NAME + " ("
                + WarehouseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WarehouseEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, "
                + WarehouseEntry.COLUMN_PRICE + " INTEGER NOT NULL, "
                + WarehouseEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + WarehouseEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "
                + WarehouseEntry.COLUMN_SUPPLIER + " INTEGER NOT NULL, "
                + WarehouseEntry.COLUMN_SUPPLIER_PHONE + " INTEGER );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKSTORE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}