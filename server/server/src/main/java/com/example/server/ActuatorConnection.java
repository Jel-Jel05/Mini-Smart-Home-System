package com.example.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ActuatorConnection {
    public  Socket socket; //the Tcp socket connection
    public PrintWriter out; //output stream
    public BufferedReader in; //input stream, public so it can be accessed in the actuator handler class
    public String currentStatus; //tracks the status of the device
    
    public ActuatorConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    //sends a command and waits for the device to respond
    public void sendCommand(String command) throws IOException {
        out.println(command);
    }
    //closes all conections
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}