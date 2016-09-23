/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.db;

import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoriteDAO implements Closeable {

    public FavoriteDAO(Context context) {
        dbHelper = new FavoritesDBHelper(context);
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    public Favorite createFavorite(BusStop busStop) {
        ContentValues values = new ContentValues();

        values.put(FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID, busStop.getId());
        values.put(FavoritesDBHelper.Favorite.COLUMN_NAME_ADDRESS, busStop.getAddress());

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
        favorite.setAddress(cursor.getString(2));

        return favorite;
    }

    private String[] allColumns = {
        FavoritesDBHelper.Favorite._ID,
        FavoritesDBHelper.Favorite.COLUMN_NAME_BUS_STOP_ID,
        FavoritesDBHelper.Favorite.COLUMN_NAME_ADDRESS
    };
    private SQLiteDatabase database;
    private FavoritesDBHelper dbHelper;

}