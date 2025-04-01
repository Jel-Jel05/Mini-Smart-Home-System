package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Actuator_Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter server IP Address: ");
            String ip = sc.nextLine();

            Socket socket = new Socket("192.168.1.27", 8081); // Open client socket in port "1234"
            InputStreamReader isr = new InputStreamReader(socket.getInputStream()); // bridge between byte streams and character streams.
            // It reads bytes from the socket's input stream and decodes them into characters.
            BufferedReader br = new BufferedReader(isr); //Reads chunks of inputs to reduce system calls for every read operation
            //It's too fast because it stores the data into the memory rather than the disk or the network

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream()); //retrieves the output stream of the socket,
            // allowing data to be sent to the connected server.

            BufferedWriter bw = new BufferedWriter(osw);

            System.out.println("Enter Actuator ID: ");
            String ID = sc.nextLine(); // Get the Actuator ID
            System.out.println("Enter Initial States(ON/OFF): ");
            String States = sc.nextLine(); // Get the Actuator initial state

            bw.write(ID + ": " + States); // Print the new added Actuator
            bw.newLine();
            bw.flush();
            System.out.println("Server Response: " + br.readLine()); // The server responds with a registration confirmation

            while (true){
                // String command = sc.nextLine();
                // if (command == null){
                //     break;
                // }

                // if (command.equals("TURN_ON")){
                //     States = "ON";
                // } else if (command.equals("TURN_OFF")) {
                //     States = "OFF";
                // }

                // bw.write(ID + ": " + States);
                // bw.newLine();
                // bw.flush();
                //System.out.println("status is: " + States);
                System.out.println("Server Response: " + br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
