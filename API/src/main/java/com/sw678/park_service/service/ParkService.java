package com.sw678.park_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParkService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // repositoty 추가해야함.

    public void saveParkInfo(){
        // 호출 되면
        log.info("=== api 호출 후 데이터 저장 로직 시작 ===");
        log.info("=== api 호출 후 데이터 저장 로직 끝 ===");
    }
}
