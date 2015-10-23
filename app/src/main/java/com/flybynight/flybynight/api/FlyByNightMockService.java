package com.flybynight.flybynight.api;

import com.flybynight.flybynight.api.objects.Flight;
import com.flybynight.flybynight.api.objects.User;
import com.flybynight.flybynight.api.response.FlightAttendantFlightsResponse;
import com.flybynight.flybynight.api.response.SignInResponse;

import org.joda.time.DateTime;

import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by closestudios on 10/11/15.
 */
public class FlyByNightMockService implements FlyByNightService {

    static String MOCK_TOKEN = "123456789";
    static String MOCK_PASSWORD = "password";
    static String MOCK_USERNAME = "user";

    @Override
    public SignInResponse signInUser(@Query("username") String username, @Query("password") String password) throws InterruptedException {
        // Process Time
        process();

        SignInResponse response = new SignInResponse();
        if(password.equals(MOCK_PASSWORD) && username.equals(MOCK_USERNAME)) {
            response.success = true;
            response.error = false;
            response.token = MOCK_TOKEN;
            response.user = getUser(username);
        } else {
            response.success = false;
            response.error = true;
            response.message = "Invalid Password or Username";
        }

        return response;
    }

    @Override
    public SignInResponse signInUser(@Query("token") String token) throws InterruptedException {
        // Process Time
        process();

        SignInResponse response = new SignInResponse();
        if(token.equals(MOCK_TOKEN)) {
            response.success = true;
            response.error = false;
            response.token = MOCK_TOKEN;
            response.user = getUser(MOCK_USERNAME);
        } else {
            response.success = false;
            response.error = true;
            response.message = "Invalid Token";
        }

        return response;
    }

    @Override
    public FlightAttendantFlightsResponse getFlightAttendantFlights() throws InterruptedException {
        // Process Time
        process();

        FlightAttendantFlightsResponse response = new FlightAttendantFlightsResponse();
        response.flights = getMockFlights(8);
        response.success = true;
        response.error = false;

        return response;
    }

    User getUser(String username) {
        User user = new User();
        user.username = username;
        return user;
    }

    Flight[] getMockFlights(int num) {
        Flight[] flights = new Flight[num];
        flights[0] = getMockFlight(0, DateTime.now().minusMinutes(60), DateTime.now().plusMinutes(60));
        for(int i=1;i<num;i++) {
            flights[i] = getMockFlight(i,DateTime.now().plusMinutes(100 * i + 120), DateTime.now().plusMinutes(100 * i + 180));
        }
        return flights;
    }

    Flight getMockFlight(int flightId, DateTime departure, DateTime arrival) {
        Flight flight = new Flight();
        flight.id = "" + flightId;
        flight.flight_num = "C" + ((flightId + 1)*123);
        flight.arrival_time = arrival;
        flight.departure_time = departure;
        flight.arrival_destination = "MSP";
        flight.departure_destination = "HEC";
        flight.number_passengers = 130;
        flight.number_seats_available = 5;
        flight.status = 0;
        flight.number_of_crew = 5;
        return flight;
    }

    void process() throws InterruptedException {
        Thread.sleep(1000);
    }
}
