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
        dbHelper = new FavoritesContract(context);
    }

    public void close() {
        dbHelper.close();
    }

    public Favorite createFavorite(String busStopId) {
        ContentValues values = new ContentValues();

        values.put(FavoritesContract.Favorite.COLUMN_NAME_BUS_STOP_ID, busStopId);

        long insertId = database.insert(FavoritesContract.Favorite.TABLE_NAME, null, values);

        Cursor cursor = database.query(
            FavoritesContract.Favorite.TABLE_NAME, allColumns,
            FavoritesContract.Favorite._ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();

        Favorite favorite = cursorToFavorite(cursor);

        cursor.close();

        return favorite;
    }

    public void deleteFavorite(Favorite favorite) {
        String[] whereArgs = { favorite.getBusStopId() };

        database.delete(
            FavoritesContract.Favorite.TABLE_NAME,
            FavoritesContract.Favorite.COLUMN_NAME_BUS_STOP_ID + " = ?", whereArgs);
    }

    public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();

        Cursor cursor = database.query(
            FavoritesContract.Favorite.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Favorite favorite = cursorToFavorite(cursor);

            favorites.add(favorite);

            cursor.moveToNext();
        }

        cursor.close();

        return favorites;
    }

    public Favorite getFavorite(String favoriteId) {
        String selection = FavoritesContract.Favorite.COLUMN_NAME_BUS_STOP_ID + "=?";
        String[] selectionArgs = {favoriteId};

        try (Cursor cursor = database.query(
                FavoritesContract.Favorite.TABLE_NAME, allColumns, selection, selectionArgs,
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
        FavoritesContract.Favorite._ID,
        FavoritesContract.Favorite.COLUMN_NAME_BUS_STOP_ID
    };
    private SQLiteDatabase database;
    private FavoritesContract dbHelper;

}