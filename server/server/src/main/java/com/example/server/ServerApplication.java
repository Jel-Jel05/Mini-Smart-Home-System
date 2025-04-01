package com.example.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
        //creates a new thread for TCP communication
		        new Thread(() -> {
            try {
                DeviceRegistry registry = DeviceRegistry.getInstance();
                ServerSocket serverSocket = new ServerSocket(8081, 0, InetAddress.getByName("0.0.0.0"));
                System.out.println("TCP Server started on port 8081");
                
                while (true) {
                    //blocks until a device connects
                    new ActuatorHandler(serverSocket.accept(), registry).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
		SpringApplication.run(ServerApplication.class, args);
	}

}
