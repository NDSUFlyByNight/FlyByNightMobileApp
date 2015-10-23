package com.flybynight.flybynight.api;

import com.flybynight.flybynight.api.response.FlightAttendantFlightsResponse;
import com.flybynight.flybynight.api.response.SignInResponse;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by closestudios on 10/11/15.
 */
public interface FlyByNightService {

    @GET("/signin")
    SignInResponse signInUser(@Query("username") String username, @Query("password") String password) throws InterruptedException;

    @GET("/signin")
    SignInResponse signInUser(@Query("token") String token) throws InterruptedException;

    @GET("/flightattendent")
    FlightAttendantFlightsResponse getFlightAttendantFlights() throws InterruptedException;

}
