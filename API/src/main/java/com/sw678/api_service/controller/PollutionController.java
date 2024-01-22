package com.sw678.api_service.controller;

import com.sw678.api_service.service.PollutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pollution-service")
public class PollutionController {
    private PollutionService pollutionService;

    @Autowired
    public PollutionController(PollutionService pollutionService) {
        this.pollutionService = pollutionService;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/load")
    public void loadData(){
        log.info("PollutionService.showAirPollutionByLocation 호출");
        pollutionService.showAirPollutionByLocation();
    }
}
