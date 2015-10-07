package com.srost_studio.assignment.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private SQLiteOpenHelper sqLiteHelper;
    private SQLiteDatabase database;
    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public void initialize() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        sqLiteHelper = new DatabaseHelper(context);
        database = sqLiteHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            initialize();
        }
        return database;
    }
}
