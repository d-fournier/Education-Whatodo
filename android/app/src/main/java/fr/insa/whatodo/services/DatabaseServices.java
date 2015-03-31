package fr.insa.whatodo.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.models.Category;
import fr.insa.whatodo.models.City;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.Tag;
import fr.insa.whatodo.utils.EventDatabaseContract;

/**
 * Created by Benjamin on 27/03/2015.
 */
public class DatabaseServices {

    private static SimpleDateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat df_time = new SimpleDateFormat("HH-mm-ss");

    public static void putAllEventsInDatabase(List<Event> listEvent, SQLiteDatabase db) {
        for (Event e : listEvent) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ID, e.getId());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_NAME, e.getName());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_SUMMARY, e.getSummary());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_URL, e.getUrl());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_TIME, df_time.format(e.getStartTime()));
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_TIME, df_time.format(e.getEndTime()));
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_DATE, df_date.format(e.getStartDate()));
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_DATE, df_date.format(e.getEndDate()));
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_PRICE, e.getPrice());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_MIN_AGE, e.getMinAge());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ADDRESS, e.getAddress());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_CITY_CODE, e.getCity().getCode());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_CITY_NAME, e.getCity().getName());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_IMAGE_URL, e.getImageURL());
            db.insert(EventDatabaseContract.EventTable.TABLE_NAME, null, values);
        }
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

    public static List<Event> getAllEvents(SQLiteDatabase db) {

        List<Event> listEvents = new ArrayList<>();
        List<Category> listCategories = new ArrayList<>();
        List<Tag> listTags = new ArrayList<>();
        String query = "SELECT * FROM " + EventDatabaseContract.EventTable.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);

        //TODO récuperer toutes les references aux catégories et tags et changer les null dans le constructeur

        Event e = null;
        if (c.moveToFirst()) {
            do {
                try {
                    e = new Event(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                            c.getString(7), c.getString(8), c.getInt(9), c.getString(10), new City(c.getString(11), c.getString(12)), null, null, c.getString(13));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                listEvents.add(e);

            } while (c.moveToNext());
        }

        return listEvents;
    }


}
