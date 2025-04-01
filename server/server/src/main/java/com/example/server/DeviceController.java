package com.example.server;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeviceController {
    private DeviceRegistry registry = DeviceRegistry.getInstance();
    

    @PostMapping("/control") //Accepts Http post requests to api/command and parses the json request to a control command obejct
    public String controlDevice(@RequestBody ControlCommand command) {
        System.out.println("Received command for device: " + command.getDeviceId());
        if (!registry.containsDevice(command.getDeviceId())) {
            return "{\"message\": \"Device not found\"}";
        }
        
        try {
            ActuatorConnection connection = registry.getConnection(command.getDeviceId());
            connection.sendCommand(command.getCommand());
            System.out.println("matl3t4");
            connection.currentStatus = command.getCommand();
            return "message recived"; //return the response wheather off or on
        } catch(Exception e) {
            return "{\"message\": \"Error communicating with device\"}"; 
        }
    }
    //control command class mapped automatically by spring to structure http requests
    static class ControlCommand {
        private String deviceId;
        private String command;
        
        // Getters and setters
        public String getDeviceId()
         { 
            return deviceId;
         }
        public void setDeviceId(String deviceId)
         { 
            this.deviceId = deviceId;
         }
        public String getCommand()
         { 
            return command;
         }
        public void setCommand(String command)
         { 
            this.command = command; 
        }
    }
}