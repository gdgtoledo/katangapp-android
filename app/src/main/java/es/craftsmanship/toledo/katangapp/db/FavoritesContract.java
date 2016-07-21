package es.craftsmanship.toledo.katangapp.db;

import android.provider.BaseColumns;

/**
 * @author Manuel de la Peña
 */
public final class FavoritesContract {

    public FavoritesContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_FAVORITES =
        "CREATE TABLE " + Favorite.TABLE_NAME + " (" +
            Favorite._ID + " INTEGER PRIMARY KEY" + COMMA_SEP + " " +
            Favorite.COLUMN_NAME_BUS_STOP_ID + " " + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_FAVORITES =
        "DROP TABLE IF EXISTS " + Favorite.TABLE_NAME;

    public static abstract class Favorite implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_BUS_STOP_ID = "busStopId";

    }

}