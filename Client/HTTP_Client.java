package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HTTP_Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Device ID: ");
        String ActuatorID = sc.nextLine();
        System.out.println("Enter Command (TURN_ON / TURN_OFF): ");
        String Command = sc.nextLine().toUpperCase();
        try {
            URL url = new URL("http://localhost:8040/api/control");
            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.setRequestMethod("POST"); // "POST": Send the data to the server
            ucon.setRequestProperty("Content-Type", "application/json"); // When the data is sent in a POST request,
            //the server needs to understand how to read the data.
            ucon.setDoOutput(true); //Send the data to the server.

            String json = String.format("{\"deviceId\":\"" + ActuatorID + "\",\"command\":\"" + Command + "\"}");
            
            try (OutputStream os = ucon.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check HTTP response code
            int responseCode = ucon.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(ucon.getInputStream(), "utf-8")
                )) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response.toString());
                }
            } else {
                // Handle errors (e.g., device not found)
                System.out.println("Error: " + ucon.getResponseMessage());
            }

        } catch (IOException e) {
            System.out.println("HTTP Request Failed: " + e.getMessage());
        }
    }
}
