package io.github.reduxr.models;

/**
 * Root response object for data received from the Aviationstack REST API.
 *
 * <p>The API returns a JSON object which contains an array of flight entries.
 * This class represents the top-level structure of that response.</p>
 *
 * <p>The {@code data} field contains an array of {@link FlightData} objects.
 * Each {@code FlightData} instance represents a single flight and aggregates
 * information about:</p>
 *
 * <ul>
 *     <li>airline (name and IATA code)</li>
 *     <li>departure airport and scheduled time</li>
 *     <li>arrival airport and scheduled time</li>
 * </ul>
 *
 * <p>In this application only the first element of the {@code data} array
 * is used, because the API request is limited to a single flight result.</p>
 */
public class ApiResponse {

    /**
     * Array of flight data objects parsed from the API response.
     */
    public FlightData[] data;
}