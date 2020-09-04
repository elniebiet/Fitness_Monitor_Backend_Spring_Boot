/*
 * created by Aniebiet Akpan
 * */

package com.project.fitnessmonitor.permissions;

import com.project.fitnessmonitor.readings.Readings;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PermissionServices {
    private PermissionsDataAccessService permissionsDataAccessService;

    @Autowired
    public PermissionServices(PermissionsDataAccessService permissionsDataAccessService) {
        this.permissionsDataAccessService = permissionsDataAccessService;
    }
    public JSONObject getPermission(String deviceRequested, String requestingDevice){
        JSONObject responseObj = new JSONObject();

        /* Insert request into DB */
        System.out.println("INSERTING DATA");
        Permissions pm = new Permissions(1, deviceRequested, requestingDevice);
        Readings readings = new Readings(deviceRequested, 0, 0, 0);

        //check first if permission is already granted i.e requestgranted = 2
        int st = permissionsDataAccessService.getStatus(pm, readings);
        if(st == 2){
            return responseObj.appendField("permissionStatus", 2); //permission already granted
        }

        int resp = permissionsDataAccessService.insertDataToDB(pm, readings);
        if(resp == 7){
            return responseObj.appendField("permissionStatus", 0); //failed request error, just return declined
        }

        //TODO: Wait for some time
        try {
            Thread.sleep(10000);
            //Done waiting for user to grantpermission, READ DB, get permission status
            int permissionStatus = permissionsDataAccessService.getStatus(pm, readings);
            //check what is the permission status is
            switch (permissionStatus){

                case 0:
                    permissionsDataAccessService.setBackToZero(pm, readings); //set permission back to zero
                    return responseObj.appendField("permissionStatus", 0); //permission not granted
                case 1:
                    permissionsDataAccessService.setBackToZero(pm, readings); //set permission back to zero
                    return responseObj.appendField("permissionStatus", 0); //permission not granted
                case 2:
                    return responseObj.appendField("permissionStatus", 2); //permission granted
                default:
                    permissionsDataAccessService.setBackToZero(pm, readings); //set permission back to zero
                    return responseObj.appendField("permissionStatus", 0);
            }

        } catch (InterruptedException ex){
            ex.printStackTrace();
            return responseObj.appendField("permissionStatus", 0); //error occured, permission not granted
        }
    }

    public JSONObject checkRequests(String requestedDevice) {
        JSONObject resp = permissionsDataAccessService.checkRequests(requestedDevice);
        return resp;
    }

    public void changePermission(Permissions permissions) {
        permissionsDataAccessService.changePermission(permissions);
    }
}
