package com.example.android.booksellerwarehouse2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.booksellerwarehouse2.data.BookWarehouseContract.WarehouseEntry;
import com.example.android.booksellerwarehouse2.data.WarehouseDbHelper;

/**
 * Allows user to create a new book.
 */

public class EditorActivity extends AppCompatActivity {

    /**
     * Displays EditText fields for book title, price, quantity, author name, and supplier info.
     */
    private EditText mTitleField;

    private EditText mPriceField;

    private EditText mQuantityField;

    private EditText mAuthorField;

    private Spinner mSupplierSpinner;

    private EditText mPhoneField;


    private int mOtherSupplier = WarehouseEntry.OTHER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
// Find all relevant views that we will need to read user input from
        mTitleField = (EditText) findViewById(R.id.title);
        mPriceField = (EditText) findViewById(R.id.price);
        mQuantityField = (EditText) findViewById(R.id.quantity);
        mAuthorField = (EditText) findViewById(R.id.author_name);
        mSupplierSpinner = (Spinner) findViewById(R.id.supplier_spinner);
        mPhoneField = (EditText) findViewById(R.id.phone);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the most common publishers.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_suppliers, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mSupplierSpinner.setAdapter(supplierSpinnerAdapter);

        mSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.penguinrandom))) {
                        mOtherSupplier = WarehouseEntry.PENGUIN;
                    } else if (selection.equals(getString(R.string.simonschuster))) {
                        mOtherSupplier = WarehouseEntry.SIMONSCHUSTER;
                    } else if (selection.equals(getString(R.string.tyndale))) {
                        mOtherSupplier = WarehouseEntry.TYNDALE;
                    } else {
                        mOtherSupplier = WarehouseEntry.OTHER;
                    }
                }
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mOtherSupplier = WarehouseEntry.OTHER;
            }
        });
    }

    /**
     * Get user input from editor and save new book into the warehouse database.
     */
    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String titleString = mTitleField.getText().toString().trim();

        String priceString = mPriceField.getText().toString().trim();
        int priceInteger = Integer.parseInt(priceString);

        String authorNameString = mAuthorField.getText().toString().trim();

        String quantityString = mQuantityField.getText().toString().trim();
        int quantityInteger = Integer.parseInt(quantityString);

        String phoneString = mPhoneField.getText().toString().trim();
        int phoneInteger = Integer.parseInt(phoneString);

        // Create database helper
        WarehouseDbHelper mDbHelper = new WarehouseDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Create a ContentValues object where column names are the keys,
        // and books attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(WarehouseEntry.COLUMN_BOOK_TITLE, titleString);
        values.put(WarehouseEntry.COLUMN_PRICE, priceInteger);
        values.put(WarehouseEntry.COLUMN_AUTHOR, authorNameString);
        values.put(WarehouseEntry.COLUMN_QUANTITY, quantityInteger);
        values.put(WarehouseEntry.COLUMN_SUPPLIER, mOtherSupplier);
        values.put(WarehouseEntry.COLUMN_SUPPLIER_PHONE, phoneInteger);

        // Insert a new row for books in the database, returning the ID of that new row.

        long newRowId = db.insert(WarehouseEntry.TABLE_NAME, null, values);
// Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Unable to add book", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Book added as item " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit activity
                finish();
            case android.R.id.home:
                // Navigate back to parent activity (BookInventoryActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
        }
        return super.onOptionsItemSelected(item);
    }
}