package com.project.fitnessmonitor.permissions;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PermissionServices {
    public PermissionServices() {
    }
    public JSONObject getPermission(String requestingDevice, String requestedDevice){
        JSONObject responseObj = new JSONObject();

        //TODO: Insert request into DB
        System.out.println("INSERTING DATA");

        //TODO: Wait for some time
        try {
            Thread.sleep(10000);
            //Done waiting
            //TODO: READ DB

            //TODO: SEND RESPONSE
            return responseObj.appendField("permissionStatus", 1);

        } catch (InterruptedException ex){
            ex.printStackTrace();
        }

        return responseObj.appendField("permissionStatus", 1);
    }
}
