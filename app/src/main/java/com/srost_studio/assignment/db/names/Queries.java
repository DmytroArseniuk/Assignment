package com.srost_studio.assignment.db.names;

public interface Queries {

    interface Create {

        String VENUE_CREATE_COMMAND = "CREATE TABLE " +
                Tables.VENUES + "(" +
                Columns.Venue.ID + " STRING PRIMARY KEY UNIQUE, " +
                Columns.Venue.BLOB + " BLOB NOT NULL, " +
                Columns.Venue.DISTANCE + " REAL NOT NULL);";

    }
}
