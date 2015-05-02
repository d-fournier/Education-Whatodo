package fr.insa.whatodo.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.model.Category;
import fr.insa.whatodo.model.City;
import fr.insa.whatodo.model.Event;
import fr.insa.whatodo.model.Tag;
import fr.insa.whatodo.utils.EventDatabaseContract;

/**
 * Created by Benjamin on 27/03/2015.
 */
public class DatabaseServices {

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
    private static final String SQL_CREATE_ENTRIES_TAG =
            "CREATE TABLE " + EventDatabaseContract.TagTable.TABLE_NAME + " (" +
                    EventDatabaseContract.TagTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.TagTable.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_ENTRIES_CATEGORY =
            "CREATE TABLE " + EventDatabaseContract.CategoryTable.TABLE_NAME + " (" +
                    EventDatabaseContract.CategoryTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    EventDatabaseContract.CategoryTable.COLUMN_NAME_NAME + TEXT_TYPE +
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

    private static SimpleDateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat df_time = new SimpleDateFormat("HH:mm:ss");

    public static void putAllEventsInDatabase(List<Event> listEvent, SQLiteDatabase db) {
        for (Event e : listEvent) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ID, e.getId());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_NAME, e.getName());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_SUMMARY, e.getDescription());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_URL, e.getUrl());
            try {
                values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_TIME, df_time.format(e.getStartTime()));
                values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_TIME, df_time.format(e.getEndTime()));
                values.put(EventDatabaseContract.EventTable.COLUMN_NAME_START_DATE, df_date.format(e.getStartDate()));
                values.put(EventDatabaseContract.EventTable.COLUMN_NAME_END_DATE, df_date.format(e.getEndDate()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_PRICE, e.getPrice());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_MIN_AGE, e.getMinAge());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_ADDRESS, e.getAddress());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_CITY_CODE, e.getCity().getCode());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_CITY_NAME, e.getCity().getName());
            values.put(EventDatabaseContract.EventTable.COLUMN_NAME_IMAGE_URL, e.getImageEvent());
            db.insert(EventDatabaseContract.EventTable.TABLE_NAME, null, values);

            putCategoryAssociationInDatabase(e, db);
            putTagAssociationInDatabase(e, db);
        }
    }

    public static void putAllCategoriesInDatabase(List<Category> list, SQLiteDatabase db) {
        for (Category c : list) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.CategoryTable.COLUMN_NAME_ID, c.getId());
            values.put(EventDatabaseContract.CategoryTable.COLUMN_NAME_NAME, c.getName());
            db.insert(EventDatabaseContract.CategoryTable.TABLE_NAME, null, values);
        }
    }

    public static void putAllCitiesInDatabase(Context ctx, SQLiteDatabase db) {
        //Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = ctx.getAssets().open("cities.db");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Path to the just created empty db
        String outFileName = "/data/data/fr.insa.whatodo/databases/" + "cities.db";

        //Open the empty db as the output stream
        OutputStream myOutput = null;
        try {
            myOutput = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        db.execSQL("ATTACH DATABASE ? AS city_db",new String[]{outFileName});

        Cursor c1 = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c1.moveToFirst()) {
            while ( !c1.isAfterLast() ) {
                System.out.println(c1.getString(0));
                c1.moveToNext();
            }
        }

        db.execSQL("INSERT INTO City SELECT city_db.whatodo_city.name, city_db.whatodo_city.ZIPcode, city_db.whatodo_city.id FROM city_db.whatodo_city");

    }


    public static void putAllTagsInDatabase(List<Tag> list, SQLiteDatabase db) {
        for (Tag t : list) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.TagTable.COLUMN_NAME_ID, t.getId());
            values.put(EventDatabaseContract.TagTable.COLUMN_NAME_NAME, t.getName());
            db.insert(EventDatabaseContract.TagTable.TABLE_NAME, null, values);
        }
    }

    public static void putTagAssociationInDatabase(Event e, SQLiteDatabase db) {
        for (Tag t : e.getTags()) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_EVENT, e.getId());
            values.put(EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_TAG, t.getId());
            db.insert(EventDatabaseContract.EventTagTable.TABLE_NAME, null, values);
        }
    }

    public static List<Tag> findTagsByEvent(int id, SQLiteDatabase db) {
        List<Tag> listT = new ArrayList<>();
        String query = "SELECT Tag."+EventDatabaseContract.TagTable.COLUMN_NAME_ID+", Tag."+EventDatabaseContract.TagTable.COLUMN_NAME_NAME+" "+
                "FROM Tag, " +
                "(SELECT * FROM EventTag WHERE EventTag."+EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_EVENT+" = "+id+") AS Tab " +
                "WHERE Tag."+EventDatabaseContract.TagTable.COLUMN_NAME_ID+" = "+"Tab."+EventDatabaseContract.EventTagTable.COLUMN_NAME_ID_TAG;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                listT.add(new Tag(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());
        }
        return listT;
    }

    public static List<Category> findCategoriesByEvent(int id, SQLiteDatabase db) {
        List<Category> listC = new ArrayList<>();
        String query = "SELECT Category."+EventDatabaseContract.CategoryTable.COLUMN_NAME_ID+", Category."+EventDatabaseContract.CategoryTable.COLUMN_NAME_NAME+" "+
                "FROM Category, " +
                    "(SELECT * FROM EventCategory WHERE EventCategory."+EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_EVENT+" = "+id+") AS Tab " +
                "WHERE Category."+EventDatabaseContract.CategoryTable.COLUMN_NAME_ID+" = "+"Tab."+EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_CATEGORY;

      Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                listC.add(new Category(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());
        }
        return listC;
    }

    public static void putCategoryAssociationInDatabase(Event e, SQLiteDatabase db) {
        for (Category c : e.getCategories()) {
            ContentValues values = new ContentValues();
            values.put(EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_EVENT, e.getId());
            values.put(EventDatabaseContract.EventCategoryTable.COLUMN_NAME_ID_CATEGORY, c.getId());
            db.insert(EventDatabaseContract.EventCategoryTable.TABLE_NAME, null, values);
        }
    }

    public static String getCityId(String cityName, String postCode,SQLiteDatabase db) {
        String query = "SELECT * FROM City";
        Cursor c;
       if(postCode!=null && !postCode.isEmpty()){
           query+=" WHERE "+ EventDatabaseContract.CityTable.COLUMN_NAME_CODE  +" = ?";
           c= db.rawQuery(query, new String[] {postCode});
       }else{
           c= db.rawQuery(query, null);
       }

       String id=null;
        if (c.moveToFirst()) {
            do {
                if(c.getString(0).equalsIgnoreCase(cityName)){
                    id=c.getString(2);
                    return id;
                }
            } while (c.moveToNext());
        }
        return id;
    }

    public static String findCityById(int id, SQLiteDatabase db) {
        String query = "SELECT * FROM City WHERE "+ EventDatabaseContract.CityTable.COLUMN_NAME_CITY_ID +" = "+id;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            return c.getString(0);
        }
        return "";
    }

    public static String getTagId(String tagName, SQLiteDatabase db) {
        String query = "SELECT * FROM Tag WHERE "+EventDatabaseContract.TagTable.COLUMN_NAME_NAME +" = ?";
        Cursor c= db.rawQuery(query, new String[] {tagName});


        String id=null;
        if (c.moveToFirst()) {
            do {
                    id=c.getString(0);
                    return id;

            } while (c.moveToNext());
        }
        return id;
    }


    public static List<Event> getAllEvents(SQLiteDatabase db) {

        List<Event> listEvents = new ArrayList<>();
        List<Category> listCategories = new ArrayList<>();
        List<Tag> listTags = new ArrayList<>();
        String query = "SELECT * FROM " + EventDatabaseContract.EventTable.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);

        Event e = null;
        if (c.moveToFirst()) {
            do {
                try {
                    listCategories = findCategoriesByEvent(c.getInt(0), db);
                    listTags = findTagsByEvent(c.getInt(0), db);
                    e = new Event(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                            c.getString(7), c.getString(8), c.getInt(9), c.getString(10), new City(c.getString(11), c.getString(12)), listTags, listCategories, c.getString(13));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                listEvents.add(e);

            } while (c.moveToNext());
        }

        return listEvents;
    }

    public static List<String> getAllCityNames(SQLiteDatabase db) {
        String query = "SELECT * FROM City";
        List<String> listNames = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                listNames.add(c.getString(0)+" "+c.getString(1));
            } while (c.moveToNext());
        }
        return listNames;
    }

    public static List<String> getAllTagsNames(SQLiteDatabase db) {
        String query = "SELECT * FROM " + EventDatabaseContract.TagTable.TABLE_NAME;
        List<String> listNames = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                listNames.add(c.getString(1));
            } while (c.moveToNext());
        }
        return listNames;
    }

    public static void updateEventTable(List<Event> list, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + EventDatabaseContract.EventTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EventDatabaseContract.EventCategoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EventDatabaseContract.EventTagTable.TABLE_NAME);
        db.execSQL(SQL_CREATE_ENTRIES_EVENT);
        db.execSQL(SQL_CREATE_ENTRIES_EVENT_CATEGORY);
        db.execSQL(SQL_CREATE_ENTRIES_EVENT_TAG);
        putAllEventsInDatabase(list, db);
    }

    public static void updateTagTable(List<Tag> list, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + EventDatabaseContract.TagTable.TABLE_NAME);
        db.execSQL(SQL_CREATE_ENTRIES_TAG);
        putAllTagsInDatabase(list, db);
    }

    public static void updateCategoryTable(List<Category> list, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + EventDatabaseContract.CategoryTable.TABLE_NAME);
        db.execSQL(SQL_CREATE_ENTRIES_CATEGORY);
        putAllCategoriesInDatabase(list, db);
    }



}
