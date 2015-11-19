package com.flybynight.flybynight.api.objects;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by closestudios on 10/11/15.
 */
public class Flight extends BaseObject implements Serializable {

    // Each flight entry should display flight number, arrival time, departure time,
    // arrival destination, departure destination, number of passengers, passenger seats available,
    // color indicating status, and crew number.

    public String flight_num;
    public DateTime arrival_time;
    public DateTime departure_time;
    public String arrival_destination;
    public String departure_destination;
    public int number_passengers;
    public int number_seats_available;
    public int status;
    public int number_of_crew;

}
