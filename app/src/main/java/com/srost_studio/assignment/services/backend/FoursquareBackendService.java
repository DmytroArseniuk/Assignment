package com.srost_studio.assignment.services.backend;

import br.com.condesales.models.Photos;
import retrofit.http.GET;
import retrofit.http.Path;
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

    @GET("/venues/{id}/photos")
    PhotoResponsePOJO getVenuePhotos(@Path("id") String id,
                                       @Query("v") String apiDateVersion,
                                       @Query("client_id") String clientId,
                                       @Query("client_secret") String clientSecret);


}
