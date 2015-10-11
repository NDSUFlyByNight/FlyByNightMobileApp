package com.flybynight.flybynight.api.response;

import com.flybynight.flybynight.api.objects.Flight;
import com.flybynight.flybynight.api.objects.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by closestudios on 10/11/15.
 */
public class FlightAttendantFlightsResponse extends Response {


    @SerializedName("flights")
    public Flight[] flights;


}
