# Flight Tracker

Flight Tracker is a Java desktop application that allows users to fetch and view real-time flight information using an external REST API.  
The application provides a simple graphical interface for entering a flight IATA code and displays structured information about the airline, departure, and arrival.


## Features

- Fetches real-time flight data from a REST API
- Parses JSON responses into structured Java objects
- Displays flight information in a Swing-based GUI
- Uses local file-based caching to reduce API requests
- External configuration via properties file
- Clean separation between API, business logic, data models, and GUI


## Technologies Used

- Java 17+
- Java Swing (GUI)
- Java HTTP Client (`java.net.http`)
- Gson (JSON parsing)
- Maven (build and dependency management)


## Application Architecture

The project is structured to clearly separate responsibilities:

- **AviationAPI**  
  Handles communication with the external REST API, builds HTTP requests, processes responses, and manages caching.

- **ConfigLoader**  
  Loads configuration values (such as API keys) from a properties file.

- **CacheManager**  
  Manages saving and loading cached API responses as JSON files.

- **JsonParser**  
  Converts raw JSON strings into Java model objects using Gson.

- **FlightManager**  
  Acts as a service layer that processes parsed data and provides formatted information for the GUI.

- **Model classes (`ApiResponse`, `FlightData`, etc.)**  
  Represent the structure of the API response and are used for JSON deserialization.

- **FlightGUI**  
  Swing-based graphical user interface for user interaction and data presentation.


## API and Data Source

This application uses the **Aviationstack API** as a data source.

API provider:  
https://aviationstack.com/

The API returns flight information in JSON format, which is deserialized into Java objects for further processing.


## Configuration

Before running the application, you must provide your own API key.

1. Register at:  
   https://aviationstack.com/

2. Create or edit the file:
   src/main/resources/config.properties

3. Add your API key:
   `aviationstack.apikey=YOUR_API_KEY_HERE`

The API key is intentionally not hardcoded and is loaded at runtime via the configuration file.


## Caching

To minimize unnecessary API calls, the application implements a simple local cache:

- API responses are saved as formatted JSON files
- Cached data is reused if available
- Cache files are stored in the `cache/` directory which will be created after the first API request
- The cache directory is created automatically if it does not exist


## Running the Application

### Using Maven

1. Build the project: 
   `mvn clean package`

2. Run the generated JAR file:
   `java -jar target/flight-tracker-1.0-SNAPSHOT.jar`

Make sure the `config.properties` file is present and correctly configured before running.


## Notes

- The project focuses on clarity, maintainability, and practical API usage
- All core logic is independent of the GUI and can be reused or extended
- The application can be easily expanded with additional flight details or UI improvements


## Extensibility

The application was designed with future extensions in mind.

While the current implementation retrieves a single flight entry per request, the architecture allows easy expansion, such as:

- handling multiple flight results returned by the API
- adding more detailed flight data (aircraft, live status, delays)
- implementing advanced search or filtering
- extending or replacing the data source without affecting the GUI

The separation between API access, business logic, data models, and the user interface ensures that new features can be added with minimal changes to existing code.

## License

This project is licensed under the MIT License.
