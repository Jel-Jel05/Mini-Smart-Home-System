package com.example.server;
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
        try {
            ActuatorConnection connection = new ActuatorConnection(socket);
            String inputLine;
            
            // splits the message to different components and registers the device with the central registry
            if ((inputLine = connection.in.readLine()) != null && inputLine.startsWith("REGISTER:")) {
                String[] parts = inputLine.split(":");
                String deviceId = parts[1];
                String initialStatus = parts[2];
                
                registry.registerDevice(deviceId, connection);
                System.out.println("Registered device: " + deviceId + " with status " + initialStatus);
            }
            

        } catch (Exception e) {
            System.out.println("Connection handler error: " + e.getMessage());
        }
    }
}