package fr.insa.whatodo.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.insa.whatodo.models.Category;
import fr.insa.whatodo.models.Event;
import fr.insa.whatodo.models.Tag;

/**
 * Created by Benjamin on 25/03/2015.
 */
public class JSonParser {


    public static ArrayList<Event> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        JsonToken token = null;
        reader.beginObject();
        while (token != JsonToken.BEGIN_ARRAY) {
            reader.skipValue();
            token = reader.peek();
        }
        try {
            return readEventsArray(reader);
        } finally {
            reader.close();
        }


    }

    public static ArrayList<Event> readEventsArray(JsonReader reader) throws IOException {
        ArrayList<Event> Events = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            Events.add(readEvent(reader));
        }
        reader.endArray();
        return Events;
    }

    public static Event readEvent(JsonReader reader) throws IOException {
        int id = -1;
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
        List<Category> categories = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
//            String name = reader.nextName();
            id = reader.nextInt();
         /*   String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("name")) {
                title = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("url")) {
                url = reader.nextString();
            } else if (name.equals("startTime")) {
                startTime = reader.nextString();
            } else if (name.equals("endTime")) {
                endTime = reader.nextString();
            } else if (name.equals("startDate")) {
                startDate = reader.nextString();
            } else if (name.equals("endDate")) {
                endDate = reader.nextString();
            } else if (name.equals("price")) {
                price = reader.nextString();
            } else if (name.equals("minAge")) {
                minAge = reader.nextInt();
            } else if (name.equals("addresses")) {
                addresses = readAddressesArray(reader);
            } else if (name.equals("categories")) {
                categories = readCategoriesArray(reader);
            } else if (name.equals("tags")) {
                tags = readTagsArray(reader);
            }*/
        }
        reader.endObject();
        return null;
    }

    public static List<String> readAddressesArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList();

        reader.beginArray();
        reader.beginObject();
        while (reader.hasNext()) {
            String jsonName = reader.nextName();
            if (jsonName.equals("localisation")) {
                strings.add(reader.nextString());
            }

        }
        reader.endObject();
        reader.endArray();
        return strings;
    }

    public static List<Tag> readTagsArray(JsonReader reader) throws IOException {
        List<Tag> tags = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
           tags.add(readTag(reader));
        }
        reader.endArray();
        return tags;
    }

    public static Tag readTag(JsonReader reader) throws IOException {
        int id=-1;
        String name="";

        reader.beginObject();
        while (reader.hasNext()) {
            String jsonName = reader.nextName();
            if (jsonName.equals("id")) {
                id = reader.nextInt();
            } else if (jsonName.equals("name")) {
                name = reader.nextString();
            }
        }
        reader.endObject();
        return new Tag(id, name);
    }

    public static List<Category> readCategoriesArray(JsonReader reader) throws IOException {
        List<Category> categories = new ArrayList();

        reader.beginArray();
        reader.beginObject();
        while (reader.hasNext()) {
           categories.add(readCategory(reader));
        }
        reader.endObject();
        reader.endArray();
        return categories;
    }

    public static Category readCategory(JsonReader reader) throws IOException {
        int id=-1;
        String name="";

        reader.beginObject();
        while (reader.hasNext()) {
            String jsonName = reader.nextName();
            if (jsonName.equals("id")) {
                id = reader.nextInt();
            } else if (jsonName.equals("name")) {
                name = reader.nextString();
            }
        }
        reader.endObject();
        return new Category(id, name);
    }
}
