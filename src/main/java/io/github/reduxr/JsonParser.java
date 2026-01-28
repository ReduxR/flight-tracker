package io.github.reduxr;

import com.google.gson.Gson;

import io.github.reduxr.models.ApiResponse;

/**
 * Utility class for parsing JSON strings into Java objects.
 * 
 * JsonParser provides methods to convert raw JSON from the Aviation API
 * into structured ApiResponse objects for easier access to flight data.
 */
public class JsonParser {

    /** Gson instance used for JSON deserialization */
    private static final Gson gson = new Gson();

    /**
     * Parses a JSON string representing flight data into an ApiResponse object.
     *
     * @param json The JSON string to parse
     * @return ApiResponse object containing the parsed flight data
     */
    public static ApiResponse parseJson(String json) {
        return gson.fromJson(json, ApiResponse.class);
    }
}