package io.github.reduxr;

import javax.swing.SwingUtilities;

/**
 * Application entry point.
 * For security reasons, the API key is not included in the JAR file.
 * The user must provide their own key in config.properties before running the application from:
 * https://aviationstack.com and place it into:
 *
 *  src/main/resources/config.properties
 *
 * Example:
 * aviationstack.apikey=YOUR_API_KEY_HERE
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlightGUI::new);
    }
}