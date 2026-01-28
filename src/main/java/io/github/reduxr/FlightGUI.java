package io.github.reduxr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * FlightGUI represents the graphical user interface of the application.
 * 
 * It allows the user to enter a flight IATA code, sends the request via
 * FlightManager and displays the processed flight information in a
 * readable and visually structured form.
 */
public class FlightGUI extends JFrame {

    /** Input field for flight IATA code */
    private JTextField iataField;

    /** Panel used for displaying flight information */
    private JPanel infoPanel;

    /**
     * Creates and initializes the main GUI window.
     */
    public FlightGUI() {
        super("Flight Info Tracker");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLayout(new BorderLayout(10, 10));

        // Top input panel (flight search)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel label = new JLabel("Enter flight IATA:");
        label.setFont(new Font("Arial", Font.BOLD, 14));

        iataField = new JTextField(12);
        iataField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));

        inputPanel.add(label);
        inputPanel.add(iataField);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Central panel for displaying flight data
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Search button action
        searchButton.addActionListener((ActionEvent e) -> searchFlight());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles the flight search logic:
     * - validates user input
     * - loads flight data via FlightManager
     * - updates GUI with received information
     */
    private void searchFlight() {
        String iata = iataField.getText().trim();

        if (iata.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a flight IATA code!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        FlightManager fm = new FlightManager();
        try {
            fm.loadFlight(iata);

            infoPanel.removeAll();

            // Airline information
            JLabel airlineLabel = new JLabel(
                    "Airline: " + fm.getAirlineName() + " (" + fm.getAirlineIata() + ")"
            );
            airlineLabel.setFont(new Font("Arial", Font.BOLD, 16));
            airlineLabel.setForeground(new Color(25, 50, 120));
            airlineLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

            infoPanel.add(airlineLabel);

            // Departure information panel
            JPanel depPanel = createFlightPanel(
                    "Departure",
                    fm.getDepartureAirport(),
                    fm.getDepartureAirportIata(),
                    fm.getDepartureScheduledTime(),
                    new Color(180, 220, 250)
            );

            infoPanel.add(depPanel);
            infoPanel.add(Box.createVerticalStrut(10));

            // Arrival information panel
            JPanel arrPanel = createFlightPanel(
                    "Arrival",
                    fm.getArrivalAirport(),
                    fm.getArrivalAirportIata(),
                    fm.getArrivalScheduledTime(),
                    new Color(200, 250, 200)
            );

            infoPanel.add(arrPanel);

            infoPanel.revalidate();
            infoPanel.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error fetching flight data:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Creates a styled panel for displaying departure or arrival information.
     *
     * @param title panel title
     * @param airport airport name
     * @param iata airport IATA code
     * @param time scheduled time
     * @param bgColor background color of the panel
     * @return configured JPanel with flight information
     */
    private JPanel createFlightPanel(
            String title,
            String airport,
            String iata,
            String time,
            Color bgColor
    ) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(bgColor);

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setFont(new Font("Monospaced", Font.PLAIN, 14));
        text.setBackground(bgColor);
        text.setText(
                "Airport: " + airport + " (" + iata + ")\n" +
                "Scheduled Time: " + time
        );

        panel.add(text, BorderLayout.CENTER);
        return panel;
    }
}