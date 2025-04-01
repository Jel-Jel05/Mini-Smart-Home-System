package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Actuator_Client {
    private String id;
    private String status;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Actuator_Client(String id, String initialStatus) {
        this.id = id;
        this.status = initialStatus;
    }
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter server IP Address: ");
            String ip = sc.nextLine();

            Socket socket = new Socket(ip, 1234); // Open client socket in port "1234"
            InputStreamReader isr = new InputStreamReader(socket.getInputStream()); // bridge between byte streams and character streams.
            // It reads bytes from the socket's input stream and decodes them into characters.
            BufferedReader br = new BufferedReader(isr); //Reads chunks of inputs to reduce system calls for every read operation
            //It's too fast because it stores the data into the memory rather than the disk or the network

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //retrieves the output stream of the socket,
            // allowing data to be sent to the connected server.

            System.out.println("Enter Actuator ID: ");
            String ID = sc.nextLine(); // Get the Actuator ID
            System.out.println("Enter Initial States(ON/OFF): ");
            String States = sc.nextLine(); // Get the Actuator initial state

            out.println(ID + ": " + States); // Print the new added Actuator
            System.out.println("Server Response: " + br.readLine()); // The server responds with a registration confirmation

            while (true){
                String command = sc.nextLine();
                if (command == null){
                    break;
                }

                if (command.equals("TURN_ON")){
                    States = "ON";
                } else if (command.equals("TURN_OFF")) {
                    States = "OFF";
                }

                out.println(ID + " is now " + States);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
