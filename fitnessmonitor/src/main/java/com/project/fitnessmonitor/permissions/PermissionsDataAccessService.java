/*
 * created by Aniebiet Akpan
 * */

package com.project.fitnessmonitor.permissions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.project.fitnessmonitor.readings.Readings;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PermissionsDataAccessService {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public PermissionsDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int insertDataToDB(Permissions permissions, Readings readings){
        //insert to tblDevices the ID requested if it doesnt exist
        String sql = "INSERT INTO tbldevices (userid, bodytemp, heartrate, numofsteps) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (userid) DO UPDATE SET bodytemp = ?, heartrate = ?, numofsteps = ?";
        try {
            jdbcTemplate.update(sql, readings.getUserId(), readings.getBodyTemp(), readings.getHeartRate(), readings.getNumOfSteps(),
                    readings.getBodyTemp(), readings.getHeartRate(), readings.getNumOfSteps());
        } catch(Exception ex){
            ex.printStackTrace();
            return 7; //error code
        }

        try {
            //check if request already exists in tblmonitorrequests table
            sql = "SELECT userid, requestingdevice, requestgranted FROM tblmonitorrequests WHERE userid = '" + permissions.getDeviceRequested() +
                    "' AND requestingdevice = '" + permissions.getRequestingDevice() + "';";
            List<Permissions> resp = jdbcTemplate.query(sql, mapRequestDetailsFromDb());
            if (resp.size() > 0) {
                //update request
                sql = "UPDATE tblmonitorrequests SET requestgranted = ? WHERE userid = ? AND requestingdevice = ?";
                jdbcTemplate.update(sql, permissions.getPermissionGranted(), permissions.getDeviceRequested(), permissions.getRequestingDevice());
                return 0;
            } else {
                //insert new request
                sql = "INSERT INTO tblmonitorrequests (userid, requestingdevice, requestgranted) VALUES (?, ?, ?) ";
                int respnse = jdbcTemplate.update(sql, readings.getUserId(), permissions.getRequestingDevice(), permissions.getPermissionGranted());
                return 0;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return 7; //error code
        }
    }
    public int getStatus(Permissions permissions, Readings readings){
        //check if permission is granted
        try {
            String sql = "SELECT userid, requestingdevice, requestgranted FROM tblmonitorrequests WHERE userid = '" + permissions.getDeviceRequested() +
                    "' AND requestingdevice = '" + permissions.getRequestingDevice() + "';";
            List<Permissions> resp = jdbcTemplate.query(sql, mapRequestDetailsFromDb());
            return resp.get(0).getPermissionGranted();
        } catch (Exception ex){
            ex.printStackTrace();
            return 7;
        }
    }
    private RowMapper<Permissions> mapRequestDetailsFromDb(){
        return (resultSet, i) -> {
            String userId = resultSet.getString("userid");
            String requestingDevice = resultSet.getString("requestingdevice");
            int requestGranted = resultSet.getInt("requestgranted");
            return new Permissions(
                    requestGranted,
                    userId,
                    requestingDevice
            );
        };
    }

    public void setBackToZero(Permissions pm, Readings readings) {
        //update request
        System.out.println("RESETTING PERMISSION...");
        String sql = "UPDATE tblmonitorrequests SET requestgranted = ? WHERE userid = ? AND requestingdevice = ?";
        try {
            jdbcTemplate.update(sql, 0, pm.getDeviceRequested(), pm.getRequestingDevice());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public JSONObject checkRequests(String requestedDevice) {
        //check if any device is requesting i.e where device is requested and requestgranted status is 1
        String sql = "SELECT userid, requestingdevice, requestgranted FROM tblmonitorrequests WHERE userid = '" + requestedDevice +
                "' AND requestgranted = 1;";
//        String sql = "SELECT userid, requestingdevice, requestgranted FROM tblmonitorrequests WHERE userid = '" + requestedDevice + "';";
        List<Permissions> resp = jdbcTemplate.query(sql, mapRequestDetailsFromDb());
        JSONObject jsonObject = new JSONObject();
        int cnt = 0;
        for (Permissions pm:resp) {
             jsonObject.appendField("" + cnt, pm.getRequestingDevice());
        }
        return jsonObject;
    }

    public void changePermission(Permissions permissions) {
        //update permission with user response
        System.out.println("UPDATING PERMISSION...");
        String sql = "UPDATE tblmonitorrequests SET requestgranted = ? WHERE userid = ? AND requestingdevice = ?";
        try {
            jdbcTemplate.update(sql, permissions.getPermissionGranted(), permissions.getDeviceRequested(), permissions.getRequestingDevice());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
