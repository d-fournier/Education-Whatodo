package fr.insa.whatodo.utils;

/**
 * Created by Benjamin on 27/03/2015.
 */
public final class EventDatabaseContract {

    public EventDatabaseContract(){}

    public static abstract class EventTable {
        public static final String TABLE_NAME = "Event";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SUMMARY = "summary";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_MIN_AGE = "min_age";
        public static final String COLUMN_NAME_ADDRESS= "address";
        public static final String COLUMN_NAME_CITY_CODE= "city_code";
        public static final String COLUMN_NAME_CITY_NAME= "city_name";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    }

    public static abstract class CategoryTable {
        public static final String TABLE_NAME = "Category";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class CityTable {
        public static final String TABLE_NAME = "City";
        public static final String COLUMN_NAME_NAME = "nme";
        public static final String COLUMN_NAME_CODE = "code";
    }

    public static abstract class TagTable {
        public static final String TABLE_NAME = "Tag";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class EventCategoryTable {
        public static final String TABLE_NAME = "EventCategory";
        public static final String COLUMN_NAME_ID_EVENT = "id_event";
        public static final String COLUMN_NAME_ID_CATEGORY = "id_category";
    }

    public static abstract class EventTagTable {
        public static final String TABLE_NAME = "EventTag";
        public static final String COLUMN_NAME_ID_EVENT = "id_event";
        public static final String COLUMN_NAME_ID_TAG = "id_tag";
    }
}
