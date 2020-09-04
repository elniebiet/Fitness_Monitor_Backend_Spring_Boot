/*
 * created by Aniebiet Akpan
 * */

package com.project.fitnessmonitor.fitnessupdate;

import com.project.fitnessmonitor.permissions.Permissions;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FitnessUpdateServiceDataAdapter {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FitnessUpdateServiceDataAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateFitnessData(FitnessUpdate fitnessUpdate) {
        //update fitness data
        try {
            String sql = "UPDATE tbldevices SET bodytemp = ?, heartrate = ?, numofsteps = ? WHERE userid = ?";
            jdbcTemplate.update(sql, fitnessUpdate.getBodyTemp(), fitnessUpdate.getHeartRate(), fitnessUpdate.getNumberOfSteps(), fitnessUpdate.getUserId());
        } catch(Exception ex){
            System.out.println("ERROR UPDATING FITNESS DATA");
        }
    }

    public JSONObject getFitnessData(String requestedDevice) {
        //get fitness data
        String sql = "SELECT userid, bodytemp, heartrate, numofsteps FROM tbldevices WHERE userid = '" + requestedDevice + "';";
        JSONObject jsonObject = new JSONObject();
        List<FitnessUpdate> resp = jdbcTemplate.query(sql, mapFitnessDataFromDb());
        for (FitnessUpdate fu: resp){
            jsonObject.appendField("bodyTemp", fu.getBodyTemp());
            jsonObject.appendField("heartRate", fu.getHeartRate());
            jsonObject.appendField("numOfSteps", fu.getNumberOfSteps());
        }
        return jsonObject;
    }

    private RowMapper<FitnessUpdate> mapFitnessDataFromDb(){
        return (resultSet, i) -> {
            String userId = resultSet.getString("userid");
            int bodyTemp = resultSet.getInt("bodytemp");
            int heartRate = resultSet.getInt("heartrate");
            int numOfSteps = resultSet.getInt("numofsteps");

            return new FitnessUpdate(
                    userId,
                    bodyTemp,
                    heartRate,
                    numOfSteps
            );
        };
    }
}
