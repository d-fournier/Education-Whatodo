package fr.insa.whatodo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.insa.whatodo.services.DatabaseServices;

/**
 * Created by Benjamin on 27/03/2015.
 */
public class EventDatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Events.db";
    public static final String DATABASE_NAME_CITY = "cities.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_EVENT =
            "CREATE TABLE " + EventDatabaseContract.EventTable.TABLE_NAME + " (" +
                    EventDatabaseContract.EventTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_SUMMARY + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_START_TIME + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_END_TIME + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_MIN_AGE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_CITY_CODE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_CITY_NAME + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTable.COLUMN_NAME_IMAGE_URL + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_ENTRIES_CATEGORY =
            "CREATE TABLE " + EventDatabaseContract.CategoryTable.TABLE_NAME + " (" +
                    EventDatabaseContract.CategoryTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.CategoryTable.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_TAG =
            "CREATE TABLE " + EventDatabaseContract.TagTable.TABLE_NAME + " (" +
                    EventDatabaseContract.TagTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.TagTable.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_CITY =
            "CREATE TABLE " + EventDatabaseContract.CityTable.TABLE_NAME + " (" +
                    EventDatabaseContract.CityTable.COLUMN_NAME_CODE + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.CityTable.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_EVENT_CATEGORY =
            "CREATE TABLE " + EventDatabaseContract.EventCategoryTable.TABLE_NAME + " (" +
                    EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_EVENT + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_CATEGORY + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_ENTRIES_EVENT_TAG =
            "CREATE TABLE " + EventDatabaseContract.EventTagTable.TABLE_NAME + " (" +
                    EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_EVENT + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_TAG + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventDatabaseContract.EventTable.TABLE_NAME + COMMA_SEP + EventDatabaseContract.EventCategoryTable.TABLE_NAME + COMMA_SEP +
                    EventDatabaseContract.EventTagTable.TABLE_NAME + COMMA_SEP + EventDatabaseContract.EventCategoryTable.TABLE_NAME +
                    COMMA_SEP + EventDatabaseContract.EventTagTable.TABLE_NAME + COMMA_SEP + EventDatabaseContract.CityTable.TABLE_NAME;

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_EVENT);
        db.execSQL(SQL_CREATE_ENTRIES_CATEGORY);
        db.execSQL(SQL_CREATE_ENTRIES_TAG);
        db.execSQL(SQL_CREATE_ENTRIES_CITY);
        db.execSQL(SQL_CREATE_ENTRIES_EVENT_CATEGORY);
        db.execSQL(SQL_CREATE_ENTRIES_EVENT_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
