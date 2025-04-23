package com.project.driverapp.controller;

import com.project.driverapp.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchAjaxController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/getRemainPassengerCount")
    public int getRemainPassengerCount(@RequestParam("recordKey") int recordKey) {
        return recordService.getRemainPassengerCount(recordKey);
    }
}
