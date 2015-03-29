package fr.insa.whatodo.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.models.Category;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.Tag;
import fr.insa.whatodo.utils.EventDatabaseContract;

/**
 * Created by Benjamin on 27/03/2015.
 */
public class DatabaseServices {

    public static void putEventInDatabase(Event e, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ID, e.getId());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_NAME, e.getName());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_SUMMARY, e.getSummary());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_URL, e.getUrl());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_TIME, e.getStartTime());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_TIME, e.getEndTime());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_DATE, e.getStartDate());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_DATE, e.getEndDate());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_PRICE, e.getPrice());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_MIN_AGE, e.getMinAge());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ADDRESS, e.getAddress());
        values.put(EventDatabaseContract.EventTable.COLUMN_NAME_IMAGE_URL, e.getImageURL());

        db.insert(EventDatabaseContract.EventTable.TABLE_NAME, null, values);

    }

    public static void putCategoryInDatabase(Category c, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(EventDatabaseContract.CategoryTable.COLUMN_NAME_ID, c.getId());
        values.put(EventDatabaseContract.CategoryTable.COLUMN_NAME_NAME, c.getName());

        db.insert(EventDatabaseContract.CategoryTable.TABLE_NAME, null, values);

    }

    public static void putTagInDatabase(Tag t, SQLiteDatabase db) {

    }

    public static void putTagAssociationInDatabase(Tag t, Event e, SQLiteDatabase db) {

    }

    public static void putCategoryAssociationInDatabase(Category t, Event e, SQLiteDatabase db) {

    }

    public static Event findEventById(int id, SQLiteDatabase db) {
        return null;
    }

    public static List<Event> findEventsByName(String name, SQLiteDatabase db) {
        return null;
    }

    public static List<Event> getAllEvents(SQLiteDatabase db) {

        List<Event> listEvents = new ArrayList<>();
        String query = "SELECT * FROM " + EventDatabaseContract.EventTable.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);

        //TODO récuperer toutes les references aux catégories et tags et changer les null dans la constructeur
        //TODO changer les String en date si possible

        Event e = null;
        if (c.moveToFirst()) {
            do {
                e = new Event(c.getInt(0), c.getString(1),  c.getString(2),  c.getString(3),  c.getString(4), c.getString(5),  c.getString(6),
                        c.getString(7),  c.getString(8),  c.getInt(9),  c.getString(10), null, null, c.getString(11));
                listEvents.add(e);
            } while (c.moveToNext());
        }

        return listEvents;
    }


}
