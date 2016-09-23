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

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.BaseColumns;

/**
 * @author Manuel de la Peña
 */
public final class FavoritesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
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
            Favorite.COLUMN_NAME_BUS_STOP_ID + " " + TEXT_TYPE + COMMA_SEP + " " +
            Favorite.COLUMN_NAME_ADDRESS + " " + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_FAVORITES =
        "DROP TABLE IF EXISTS " + Favorite.TABLE_NAME;

    public static abstract class Favorite implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_BUS_STOP_ID = "busStopId";

    }

}