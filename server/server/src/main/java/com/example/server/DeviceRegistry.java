
//both service layer and regsitery layer
package com.example.server;

import java.util.concurrent.ConcurrentHashMap;
//ensures one instance across the application to prevent race conditions (singelton pattern)
public class DeviceRegistry {
    private static DeviceRegistry instance;
    private ConcurrentHashMap<String, ActuatorConnection> devices;
    
    private DeviceRegistry() {
        devices = new ConcurrentHashMap<>();
    }
    
    public static synchronized DeviceRegistry getInstance() {
        if (instance == null) {
            instance = new DeviceRegistry();
        }
        return instance;
    }
    
    public void registerDevice(String id, ActuatorConnection connection) {
        devices.put(id, connection);
    }
    
    public ActuatorConnection getConnection(String id) {
        return devices.get(id);
    }
    
    public boolean containsDevice(String id) {
        return devices.containsKey(id);
    }
}