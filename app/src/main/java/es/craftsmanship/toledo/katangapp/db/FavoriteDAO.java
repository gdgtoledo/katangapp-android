package es.craftsmanship.toledo.katangapp.db;

import es.craftsmanship.toledo.katangapp.db.model.Favorite;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoriteDAO {

    public FavoriteDAO(Context context) {
        dbHelper = new FavoritesDBHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public Favorite createFavorite(String busStopId) {
        ContentValues values = new ContentValues();

        values.put(FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID, busStopId);

        long insertId = database.insert(FavoritesDBHelper.Favorite.TABLE_NAME, null, values);

        try (Cursor cursor = database.query(
                FavoritesDBHelper.Favorite.TABLE_NAME, allColumns,
                FavoritesDBHelper.Favorite._ID + " = " + insertId, null, null, null, null)) {

            cursor.moveToFirst();

            Favorite favorite = cursorToFavorite(cursor);

            return favorite;
        }

    }

    public void deleteFavorite(Favorite favorite) {
        String[] whereArgs = { favorite.getBusStopId() };

        database.delete(
            FavoritesDBHelper.Favorite.TABLE_NAME,
            FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID + " = ?", whereArgs);
    }

    public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();

        try (Cursor cursor = database.query(
                FavoritesDBHelper.Favorite.TABLE_NAME, allColumns, null, null, null, null, null)) {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Favorite favorite = cursorToFavorite(cursor);

                favorites.add(favorite);

                cursor.moveToNext();
            }
        }

        return favorites;
    }

    public Favorite getFavorite(String favoriteId) {
        String selection = FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID + "=?";
        String[] selectionArgs = {favoriteId};

        try (Cursor cursor = database.query(
                FavoritesDBHelper.Favorite.TABLE_NAME, allColumns, selection, selectionArgs,
                null, null, null)) {

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                return cursorToFavorite(cursor);
            }
        }

        return null;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private Favorite cursorToFavorite(Cursor cursor) {
        Favorite favorite = new Favorite();

        favorite.setId(cursor.getLong(0));
        favorite.setBusStopId(cursor.getString(1));

        return favorite;
    }

    private String[] allColumns = {
        FavoritesDBHelper.Favorite._ID,
        FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID
    };
    private SQLiteDatabase database;
    private FavoritesDBHelper dbHelper;

}