package com.project.fitnessmonitor.fitnessupdate;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/fitnessupdate")
public class FitnessUpdateController {

    private FitnessUpdateService fitnessUpdateService;
    @Autowired
    public FitnessUpdateController(FitnessUpdateService fitnessUpdateService) {
        this.fitnessUpdateService = fitnessUpdateService;
    }
    @PostMapping
    public void updateFitnessData(@RequestBody FitnessUpdate fitnessUpdate){
        fitnessUpdateService.updateFitnessData(fitnessUpdate);
    }
    @GetMapping(path = "getupdate/{requestedDevice}")
    public JSONObject getFitnessData (@PathVariable("requestedDevice") String requestedDevice){
            return fitnessUpdateService.getFitnessData(requestedDevice);
    }

}
