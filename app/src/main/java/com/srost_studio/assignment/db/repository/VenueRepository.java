package com.srost_studio.assignment.db.repository;

import com.srost_studio.assignment.db.DatabaseManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.condesales.models.Venue;

public class VenueRepository {

    private final DatabaseManager databaseManager;

    public VenueRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveAll(List<Venue> venues) {
        //todo save all venues to db.
    }

    public List<Venue> findAll() {
        //todo get all data from db in ascending order of distance.
        return Collections.emptyList();
    }

    public Venue findOne(String id) {
        //todo find one venue by id.
        return null;
    }


}
