package com.sw678.api_service.controller;

import com.sw678.api_service.model.dto.ParkDto;
import com.sw678.api_service.service.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/park-service")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkController {
    private ParkService parkService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ParkController(ParkService parkService){
        this.parkService = parkService;
    }

    @GetMapping("/load")
    public List<ParkDto> loadData(){
        // 공원 정보는 실시간으로 부를 필요 x
        // 프로젝트 시작 시에만 가져와서 db 업데이트하면 될듯.
        log.info("parkService.saveParkInfo 호출");
        return parkService.saveParkInfo();
    }
}
