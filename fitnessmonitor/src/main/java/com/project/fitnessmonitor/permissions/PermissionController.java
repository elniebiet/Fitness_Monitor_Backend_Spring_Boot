package com.project.fitnessmonitor.permissions;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
