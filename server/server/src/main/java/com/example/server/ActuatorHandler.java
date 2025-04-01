package com.example.server;
import java.io.PrintWriter;
import java.net.Socket;
//extends thread to run as independet exection thread
//each device gets its own thread
public class ActuatorHandler extends Thread {
    private Socket socket; //the tcp socket connection
    private DeviceRegistry registry;
    
    public ActuatorHandler(Socket socket, DeviceRegistry registry) {
        this.socket = socket;
        this.registry = registry;
    }
    
    public void run() {
        String deviceId=" ";
        try {
            ActuatorConnection connection = new ActuatorConnection(socket);
            PrintWriter out = connection.out;
            String inputLine;
            
            // splits the message to different components and registers the device with the central registry
            if ((inputLine = connection.in.readLine()) != null) {
                String[] parts = inputLine.split(":");
                deviceId = parts[0];
                String initialStatus = parts[1];
                
                registry.registerDevice(deviceId, connection);
                out.println("Data recived");
                System.out.println("Registered device: " + deviceId + " with status " + initialStatus);
            }
            

        } catch (Exception e) {
            System.out.println("Connection with " + deviceId +" handler error: " + e.getMessage());
        }
        while (true) { 
            try {
                ActuatorConnection connection = new ActuatorConnection(socket);
                PrintWriter out = connection.out;
                String inputLine;
                
                if ((inputLine = connection.in.readLine()) != null) {
                    String[] parts = inputLine.split(":");
                    deviceId = parts[0];
                    String initialStatus = parts[1];
                    
                    registry.registerDevice(deviceId, connection);
                    out.println("Data recived");
                }
                
    
            } catch (Exception e) {
                System.out.println("Connection with " + deviceId +" is off ");
                break;
            }
        }
    }
}