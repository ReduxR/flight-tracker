package io.github.reduxr;

import io.github.reduxr.models.ApiResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * AviationAPI is a utility class responsible for fetching flight data
 * from the AviationStack REST API.
 * 
 * It handles caching, HTTP requests, and JSON parsing into ApiResponse objects.
 */
public class AviationAPI {

    /** API key loaded from config.properties */
    private static final String API_KEY = ConfigLoader.getKey("aviationstack.apikey");

    /** Base URL for AviationStack flights endpoint */
    private static final String FLIGHTS_URL = "https://api.aviationstack.com/v1/flights";

    /** Gson instance configured for pretty-printing JSON */
    private static final Gson PRETTY_JSON = new GsonBuilder().setPrettyPrinting().create(); 

    /**
     * Fetches flight data for a given IATA flight number.
     * 
     * This method first checks if cached data exists. If so, it loads and parses the cached JSON.
     * Otherwise, it sends an HTTP GET request to the AviationStack API, parses the response, 
     * stores a pretty-printed copy in the cache, and returns the parsed ApiResponse.
     *
     * @param flightNumber IATA code of the flight to fetch
     * @return ApiResponse object containing flight data
     * @throws Exception if network issues occur, the cache cannot be accessed, or parsing fails
     */
    public static ApiResponse getFlightData(String flightNumber) throws Exception {

        // Check if cached JSON exists and return parsed object if available
        if (CacheManager.hasCache(flightNumber)) {
            String loadedCache = CacheManager.loadCache(flightNumber);
            return JsonParser.parseJson(loadedCache);
        }

        // Construct request URL with API key and flight IATA
        String requestURL = FLIGHTS_URL
                + "?access_key=" + API_KEY
                + "&flight_iata=" + flightNumber
                + "&limit=1";

        // Prepare and send HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Handle non-200 HTTP responses
        if (response.statusCode() != 200) {
            throw new RuntimeException("Request error: " + response.statusCode());
        }

        String responseResult = response.body();

        // Pretty-print JSON for cache storage
        com.google.gson.JsonElement jsonElement = com.google.gson.JsonParser.parseString(responseResult);
        String pretty = PRETTY_JSON.toJson(jsonElement);
        CacheManager.saveCache(flightNumber, pretty);

        // Parse JSON into structured ApiResponse
        return JsonParser.parseJson(responseResult);
    }
}