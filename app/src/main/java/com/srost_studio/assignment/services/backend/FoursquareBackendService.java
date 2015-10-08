package com.srost_studio.assignment.services.backend;

import java.util.List;

import br.com.condesales.models.Venue;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FoursquareBackendService {

    @GET("/venues/explore")
   ExploreResponsePOJO findVenues(@Query("v") String apiDateVersion,
                           @Query("ll") String latitudeComaLongitude,
                           @Query("query") String query,
                           @Query("limit") int limit,
                           @Query("offset") int offset,
                           @Query("client_id") String clientId,
                           @Query("client_secret") String clientSecret);
}
