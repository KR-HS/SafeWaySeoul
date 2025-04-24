package com.project.userapp.controller;

import com.project.userapp.command.LocationVO;
import com.project.userapp.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationAPIController {

    @Autowired
    private LocationService locationService;


    @GetMapping("/{recordKey}")
    public List<LocationVO> getLocationHistory(@PathVariable int recordKey){

        return locationService.selectByRecordKey(recordKey);
    }
}
