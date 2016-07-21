package es.craftsmanship.toledo.katangapp.db;

import android.provider.BaseColumns;

/**
 * @author Manuel de la Peña
 */
public final class FavoritesContract {

    public FavoritesContract() {}

    public static abstract class Favorite implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_FAVORITE_ID = "favoriteId";
        public static final String COLUMN_NAME_BUS_STOP_ID = "busStopId";

    }

}