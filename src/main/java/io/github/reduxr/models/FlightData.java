package io.github.reduxr.models;

/**
 * Represents a single flight entity returned by the Aviationstack API.
 * Groups together airline information and departure/arrival details.
 */

public class FlightData {
   public Flight flight;
   public Airline airline;
   public Departure departure;
   public Arrival arrival;
}
