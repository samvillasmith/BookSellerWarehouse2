package com.example.android.booksellerwarehouse2;

import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.android.booksellerwarehouse2.data.BookWarehouseContract.WarehouseEntry;
import com.example.android.booksellerwarehouse2.data.WarehouseDbHelper;

/**
 * Displays list of books that were entered into the app.
 */

public class WarehouseActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private WarehouseDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backroom);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarehouseActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new WarehouseDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showWarehouse();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the book warehouse database.
     */
    private void showWarehouse() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = new String[]{
                WarehouseEntry._ID,
                WarehouseEntry.COLUMN_BOOK_TITLE,
                WarehouseEntry.COLUMN_PRICE,
                WarehouseEntry.COLUMN_QUANTITY,
                WarehouseEntry.COLUMN_AUTHOR,
                WarehouseEntry.COLUMN_SUPPLIER,
                WarehouseEntry.COLUMN_SUPPLIER_PHONE
        };

        Cursor cursor = db.query(
                WarehouseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = findViewById(R.id.text_centralization);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The books table contains <number of rows in Cursor> pets.
            // All separated by a paragraph _id, title, price, quantity, author name, supplier name
            //and supplier phone number.
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("Current books in warehouse: " + cursor.getCount() + ".\n\n");

            displayView.append(WarehouseEntry.TABLE_NAME + " data criteria are as follows:\n\n");
            displayView.append(WarehouseEntry._ID + " \n " +
                    WarehouseEntry.COLUMN_BOOK_TITLE + " \n " +
                    WarehouseEntry.COLUMN_PRICE + " \n " +
                    WarehouseEntry.COLUMN_QUANTITY + " \n " +
                    WarehouseEntry.COLUMN_AUTHOR + " \n " +
                    WarehouseEntry.COLUMN_SUPPLIER + " \n " +
                    WarehouseEntry.COLUMN_SUPPLIER_PHONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(WarehouseEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_BOOK_TITLE);
            int priceColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_QUANTITY);
            int supplierAuthorColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_AUTHOR);
            int supplierNameColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_SUPPLIER);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_SUPPLIER_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentNameSupplier = cursor.getString(supplierNameColumnIndex);
                String currentAuthor = cursor.getString(supplierAuthorColumnIndex);
                String currentPhoneSupplier = cursor.getString(supplierPhoneColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +
                        currentID + " \n " +
                        currentName + " \n " +
                        currentPrice + " \n " +
                        currentQuantity + " \n " +
                        currentAuthor + "\n" +
                        currentNameSupplier + " \n " +
                        currentPhoneSupplier + " \n "));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }


}















