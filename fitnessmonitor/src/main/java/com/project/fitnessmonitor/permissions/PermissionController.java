/*
* created by Aniebiet Akpan
* */

package com.project.fitnessmonitor.permissions;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/permissions")
public class PermissionController {
    private final PermissionServices permissionService;

    @Autowired
    public PermissionController(PermissionServices permission) {
        this.permissionService = permission;
    }
    @GetMapping(path = "{requestingDevice}/{requestedDevice}")
    public JSONObject checkPermission(
            @PathVariable("requestingDevice") String requestingDevice,
            @PathVariable("requestedDevice") String requestedDevice){

            return permissionService.getPermission(requestingDevice, requestedDevice);
    }

    @GetMapping(path = "checkrequests/{requestedDevice}")
    public JSONObject checkRequests( @PathVariable("requestedDevice") String requestedDevice){
            return permissionService.checkRequests(requestedDevice);
    }
    @PostMapping
    public void changePermission(@RequestBody Permissions permissions){
        permissionService.changePermission(permissions);
    }

}
