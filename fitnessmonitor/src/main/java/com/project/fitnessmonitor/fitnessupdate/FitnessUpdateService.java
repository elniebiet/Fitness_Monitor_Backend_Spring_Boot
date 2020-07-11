package com.project.fitnessmonitor.fitnessupdate;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FitnessUpdateService {
    private FitnessUpdateServiceDataAdapter fitnessUpdateServiceDataAdapter;

    @Autowired
    public FitnessUpdateService(FitnessUpdateServiceDataAdapter fitnessUpdateServiceDataAdapter) {
        this.fitnessUpdateServiceDataAdapter = fitnessUpdateServiceDataAdapter;
    }

    public void updateFitnessData(FitnessUpdate fitnessUpdate) {
        fitnessUpdateServiceDataAdapter.updateFitnessData(fitnessUpdate);
    }

    public JSONObject getFitnessData(String requestedDevice) {
        return fitnessUpdateServiceDataAdapter.getFitnessData(requestedDevice);
    }
}
