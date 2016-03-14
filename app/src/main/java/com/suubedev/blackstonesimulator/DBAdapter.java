package com.suubedev.blackstonesimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends SQLiteOpenHelper {
    public static final String COLUMN_ROWID = "_id";
    public static final String COLUMN_STONECOUNT = "stone_count";

    private static final String DATABASE_NAME = "History.db";
    private static final String DATABASE_TABLE = "stoneTable";
    private static final int DATABASTE_VERSION = 1;

        public DBAdapter(Context context) {
            super(context, DATABASE_NAME, null, DATABASTE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            COLUMN_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_STONECOUNT + " INTEGER NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

        public void enterCount(Integer stone) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_STONECOUNT, stone);

            db.insert(DATABASE_TABLE, null, values);
            db.close();
        }

        public List<Integer> getAllCounts() {
            List<Integer> counts = new ArrayList<Integer>();
            String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    counts.add(c.getInt((c.getColumnIndex(COLUMN_STONECOUNT))));
                } while (c.moveToNext());
            }
            return counts;
        }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBAdapter.DATABASE_TABLE, null, null);
    }
}