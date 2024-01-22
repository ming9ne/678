package com.sw678.api_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw678.api_service.model.dto.ParkDto;
import com.sw678.api_service.model.dto.PollutionDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PollutionService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    @Autowired
    public PollutionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void showAirPollutionByLocation() {
        int startPage = 1;
        int maxPage = 30;
        String url = "http://openapi.seoul.go.kr:8088/7246436c7a746e733437454d506762/json/ListAirQualityByDistrictService/"
                        + startPage + "/" + maxPage;

        try {
            log.info("=== api 호출 후 데이터 저장 로직 시작 ===\n");

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            // API 호출 후 JSON 데이터 파싱
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntity.getBody());
            JSONObject jsonData = (JSONObject) jsonObject.get("ListAirQualityByDistrictService");
            JSONArray totalData = (JSONArray) jsonData.get("row");
            //System.out.println(totalData);

            List<PollutionDto> pollutionDtoList = new ArrayList<>();

            for(Object data : totalData){
                JSONObject object = (JSONObject) data;
                PollutionDto pollutionDto = makeDtoByJsonData(object);
                System.out.println(pollutionDto);
            }
        } catch (Exception e) {
            log.warn("초기 API 호출 중 오류 발생");
            e.printStackTrace();
            return;
        }
        log.info("\n=== 대기현황 api 호출 끝 ===\n");
    }

    private static PollutionDto makeDtoByJsonData(JSONObject pollution){
        String dateStr = (String)pollution.get("MSRDATE");
        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        // 문자열 -> Date
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
     //   System.out.println(localDateTime);
        String parsedDate = localDateTime.
                format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시"));
       // System.out.println(parsedDate);

        PollutionDto pollutionDto = PollutionDto.builder()
//                .msrId(Long.parseLong((String) pollution.get("P_IDX")))
                .msrCode((String) pollution.get("MSRADMCODE"))
                .msrName((String) pollution.get("MSRSTENAME"))
                .msrDate(parsedDate)
                .grade((String)pollution.get("GRADE"))
                .pm10((String)pollution.get("PM10"))
                .pm25((String) pollution.get("PM25"))
                .build();
        return pollutionDto;
    }
}
