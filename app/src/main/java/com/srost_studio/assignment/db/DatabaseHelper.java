package com.srost_studio.assignment.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.srost_studio.assignment.db.names.Queries;
import com.srost_studio.assignment.db.names.Tables;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;

    public static final String[] CREATE_QUERIES = new String[]{
            Queries.Create.VENUE_CREATE_COMMAND
    };

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.class.getCanonicalName(), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String query : CREATE_QUERIES) {
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < Tables.values().length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.values()[i] + ";");
        }
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
