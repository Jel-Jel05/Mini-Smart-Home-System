package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HTTP_Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter IP Address: ");
        String ip = sc.nextLine();


        System.out.println("Enter Actuator ID: ");
        String ActuatorID = sc.nextLine();
        System.out.println("Enter Command (TURN_ON / TURN_OFF): ");
        String Command = sc.nextLine();

        try {
            URL url = new URL("HTTP://" + ip + ":8080/control");
            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.setRequestMethod("POST"); // "POST": Send the data to the server
            ucon.setRequestProperty("Content-Type", "application/json"); // When the data is sent in a POST request,
            // the server needs to understand how to read the data.
            ucon.setDoOutput(true); //Send the data to the server.

            String json = "{\"ID\":\"" + ActuatorID + "\",\"Command\":\"" + Command + "\"}";
            try (OutputStream Ostream  = ucon.getOutputStream()){
                Ostream.write(json.getBytes());
                Ostream.flush();
            }

            InputStreamReader isr = new InputStreamReader(ucon.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            while (br.readLine() != null){
                System.out.println(br);
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
