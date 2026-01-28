package io.github.reduxr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for caching flight JSON data locally.
 * 
 * CacheManager handles checking for existing cache, saving JSON to cache,
 * and loading cached JSON from the local file system.
 */
public class CacheManager {

    /**
     * Checks whether a cached JSON file exists for the given flight number.
     * If the cache folder does not exist, it will be created.
     *
     * @param flightNumber IATA code or identifier for the flight
     * @return true if the cache file exists, false otherwise
     * @throws IOException If creating directories or accessing files fails
     */
    public static boolean hasCache(String flightNumber) throws IOException {
        // Path to the cache folder
        Path cachePath = Paths.get("cache");

        // Create cache folder if it doesn't exist
        if (!Files.exists(cachePath)) {
            Files.createDirectories(cachePath); 
        }

        // Path to the specific flight cache file
        Path flightFile = cachePath.resolve(flightNumber + ".json");

        // Return whether the flight file exists
        return Files.exists(flightFile);
    }

    /**
     * Saves the given JSON data to a cache file corresponding to the flight number.
     * Creates the cache folder if it doesn't exist.
     *
     * @param flightNumber IATA code or identifier for the flight
     * @param jsonData The JSON string to save
     * @throws IOException If writing the file fails
     */
    public static void saveCache(String flightNumber, String jsonData) throws IOException {
        // Ensure cache folder exists
        Path cachePath = Paths.get("cache");
        if (!Files.exists(cachePath)) {
            Files.createDirectories(cachePath);
        }

        // Write JSON string to file
        File file = new File("cache", flightNumber + ".json");
        Files.writeString(file.toPath(), jsonData);
    }

    /**
     * Loads the cached JSON data for the given flight number.
     * Returns null if the cache does not exist.
     *
     * @param flightNumber IATA code or identifier for the flight
     * @return Cached JSON string or null if cache does not exist
     * @throws IOException If reading the file fails
     */
    public static String loadCache(String flightNumber) throws IOException {
        // Return null if cache does not exist
        if (!hasCache(flightNumber)) {
            return null;
        }

        // Read all bytes from the cache file and convert to String
        File file = new File("cache", flightNumber + ".json");
        try (InputStream is = new FileInputStream(file)) {
            return new String(is.readAllBytes());
        }
    }
}