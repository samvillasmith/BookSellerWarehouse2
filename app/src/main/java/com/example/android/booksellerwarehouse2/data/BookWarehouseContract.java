package com.example.android.booksellerwarehouse2.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Book Inventory app.
 */
public final class BookWarehouseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookWarehouseContract() {
    }

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class WarehouseEntry implements BaseColumns {

        /**
         * Name of database table for books
         */
        public final static String TABLE_NAME = "bookstore";

        /**
         * Unique ID number for the book (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Title of the Book.
         */
        public final static String COLUMN_BOOK_TITLE = "book_title";

        /**
         * Name of the Author.
         */

        public final static String COLUMN_AUTHOR = "book_author";

        /**
         * Book's retail price.
         */
        public final static String COLUMN_PRICE = "book_price";

        /**
         * Books to go into the warehouse.
         */
        public final static String COLUMN_QUANTITY = "book_quantity";

        /**
         * Supplier
         */
        public final static String COLUMN_SUPPLIER = "supplier";

        /**
         * Supplier contact information
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";

        /**
         * <p>
         * The spinner options are {@link #OTHER}, {@link #PENGUIN},
         * {@link #SIMONSCHUSTER} or {@link #TYNDALE}.
         * Type: INTEGER notice all other values are Type: TEXT
         */
        public final static int OTHER = 0;
        public final static int PENGUIN = 1;
        public final static int SIMONSCHUSTER = 2;
        public final static int TYNDALE = 3;
    }
}