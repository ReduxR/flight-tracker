package io.github.reduxr;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import io.github.reduxr.models.ApiResponse;
import io.github.reduxr.models.FlightData;

/**
 * FlightManager is responsible for handling and presenting
 * processed flight data for the application.
 * 
 * It acts as a bridge between the raw API response and the GUI layer,
 * exposing only the required flight information in a user-friendly format.
 */
public class FlightManager {
    
    /** Full API response returned by AviationAPI */
    private ApiResponse response;

    /** Selected flight data (first result from API response) */
    private FlightData flight;

    /** Date-time format used for displaying time to the user */
    private static final DateTimeFormatter USER_DATA_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * Converts raw ISO-8601 date-time string into a user-friendly format.
     *
     * @param rawTime raw date-time string from API (ISO-8601)
     * @return formatted date-time string or "unknown" if input is null or empty
     */
    private String formatTime(String rawTime) {
        if (rawTime == null || rawTime.isBlank()) {
            return "unknown";
        }
        
        OffsetDateTime time = OffsetDateTime.parse(rawTime);
        return time.format(USER_DATA_FORMAT);
    }

    /**
     * Loads flight data for a given flight number using the AviationAPI.
     *
     * @param flightNumber IATA flight number
     * @throws Exception if no flight data is found or API request fails
     */
    public void loadFlight(String flightNumber) throws Exception {
        response = AviationAPI.getFlightData(flightNumber);

        if (response.data == null || response.data.length == 0) {
            throw new RuntimeException("No flight data found");
        }

        // Store first flight result (API limit = 1)
        flight = response.data[0];
    }

    /** @return flight number */
    public String getFlightNumber() {
        return flight.flight.number;
    }

    /** @return full flight IATA code */
    public String getFlightIata() {
        return flight.flight.iata;
    }

    /** @return airline name */
    public String getAirlineName() {
        return flight.airline.name;
    }

    /** @return airline IATA code */
    public String getAirlineIata() {
        return flight.airline.iata;
    }

    /** @return departure airport name */
    public String getDepartureAirport() {
        return flight.departure.airport;
    }

    /** @return departure airport IATA code */
    public String getDepartureAirportIata() {
        return flight.departure.iata;
    }

    /** @return formatted scheduled departure time */
    public String getDepartureScheduledTime() {
        return formatTime(flight.departure.scheduled);
    }

    /** @return arrival airport name */
    public String getArrivalAirport() {
        return flight.arrival.airport;
    }

    /** @return arrival airport IATA code */
    public String getArrivalAirportIata() {
        return flight.arrival.iata;
    }

    /** @return formatted scheduled arrival time */
    public String getArrivalScheduledTime() {
        return formatTime(flight.arrival.scheduled);
    }
}