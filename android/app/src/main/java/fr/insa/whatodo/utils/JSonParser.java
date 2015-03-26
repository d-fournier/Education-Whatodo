package fr.insa.whatodo.utils;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.insa.whatodo.models.Event;

/**
 * Created by Benjamin on 25/03/2015.
 */
public class JSonParser {


    public static ArrayList<Event> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readEventsArray(reader);
        }finally{
            reader.close();
        }


    }

    public static ArrayList<Event> readEventsArray(JsonReader reader) throws IOException {
        ArrayList<Event> Events = new ArrayList();

        reader.beginObject();
        reader.skipValue(); //Skip count
        reader.skipValue(); //Skip next
        reader.skipValue(); //Skip previous
        reader.skipValue(); //Skip results
        reader.beginArray();
        while (reader.hasNext()) {
            Events.add(readEvent(reader));
        }
        reader.endArray();
        reader.endObject();
        return Events;
    }

    public static Event readEvent(JsonReader reader) throws IOException {
        String name = null;
        String title = null;
        String description = null;
        String url = null;
        String startTime = null;
        String endTime = null;
        String startDate = null;
        String endDate = null;
        String price = null;
        int minAge = -1;
        List<String> addresses = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            name = reader.nextName();
            if (name.equals("id")) {
            } else if (name.equals("name")) {
                title = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("url")) {
                url = reader.nextString();
            } else if (name.equals("startTime")) {
                startTime = reader.nextString();
            }else if (name.equals("endTime")) {
                endTime = reader.nextString();
            }else if (name.equals("startDate")) {
                startDate = reader.nextString();
            }else if (name.equals("endDate")) {
                endDate = reader.nextString();
            }else if (name.equals("price")) {
                price = reader.nextString();
            }else if (name.equals("min_age")) {
                minAge = reader.nextInt();
            }else if (name.equals("addresses")) {
                addresses = readStringArray(reader);
            }else if (name.equals("categories")) {
                addresses = readStringArray(reader);
            }else if (name.equals("tags")) {
                addresses = readStringArray(reader);
            }
        }
        reader.endObject();
        return new Event(null, new Date(startDate), new Date(endDate), title, price, addresses.get(0), description);
    }

    public static List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }
}
