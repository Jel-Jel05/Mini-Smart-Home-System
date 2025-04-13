
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
    
    public boolean registerDevice(String id, ActuatorConnection connection) {
        if(devices.containsKey(id)){
            return false;
        }
        devices.put(id, connection);
        return true;
    }
    
    public ActuatorConnection getConnection(String id) {
        return devices.get(id);
    }
    
    public boolean containsDevice(String id) {
        return devices.containsKey(id);
    }

    public void DeleteDevice(String id){
        devices.remove(id);
    }
}