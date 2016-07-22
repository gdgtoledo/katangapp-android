package es.craftsmanship.toledo.katangapp.db;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.BaseColumns;

/**
 * @author Manuel de la Peña
 */
public final class FavoritesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Katanga.db";

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVORITES);

        onCreate(db);
    }

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