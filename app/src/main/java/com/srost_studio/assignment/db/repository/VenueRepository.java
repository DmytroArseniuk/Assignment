package com.srost_studio.assignment.db.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.srost_studio.assignment.db.DatabaseManager;
import com.srost_studio.assignment.db.names.Columns;
import com.srost_studio.assignment.db.names.Tables;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.condesales.models.Venue;

public class VenueRepository {

    private final DatabaseManager databaseManager;

    public VenueRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveAll(List<Venue> venues) {
        final SQLiteDatabase database = databaseManager.getDatabase();
        database.beginTransaction();
        for (Venue venue : venues) {
            insertVenue(venue, database);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void insertVenue(Venue venue, SQLiteDatabase database) {
        database.insertWithOnConflict(Tables.VENUES.name(), null,
                extractContentValues(venue),
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    private ContentValues extractContentValues(Venue venue) {
        ContentValues values = new ContentValues();
        values.put(Columns.Venue.ID.name(), venue.getId());
        values.put(Columns.Venue.BLOB.name(), serialize(venue));
        values.put(Columns.Venue.DISTANCE.name(), venue.getLocation().getDistance());
        return values;
    }

    private byte[] serialize(Venue obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private Venue deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is;
        Venue venue = null;
        try {
            is = new ObjectInputStream(in);
            venue = (Venue) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return venue;
    }

    public ArrayList<Venue> findAll() {
        Cursor cursor = null;
        try {
            ArrayList<Venue> venues = new ArrayList<>();
            cursor = databaseManager.getDatabase()
                    .rawQuery("SELECT * FROM " + Tables.VENUES.name()
                            + " ORDER BY " + Columns.Venue.DISTANCE.name()
                            + " ASC", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    venues.add(deserialize(cursor.getBlob(Columns.Venue.BLOB.ordinal())));
                } while (cursor.moveToNext());
            }
            return venues;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Venue findVenue(String id) {
        Cursor cursor = null;
        try {
            Venue venue = null;
            cursor = databaseManager.getDatabase()
                    .rawQuery("SELECT * FROM " + Tables.VENUES.name()
                            + " WHERE " + Columns.Venue.ID.name()
                            + " =?"
                            , new String[]{id});
            if (cursor != null && cursor.moveToFirst()) {
                venue = deserialize(cursor.getBlob(Columns.Venue.BLOB.ordinal()));
            }
            return venue;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void clear(){
        databaseManager.getDatabase().delete(Tables.VENUES.name(), null, null);
    }


}
