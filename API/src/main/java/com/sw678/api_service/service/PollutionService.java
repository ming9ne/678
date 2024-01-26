package com.sw678.api_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw678.api_service.model.dto.PollutionDto;
import com.sw678.api_service.repository.PollutionRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private PollutionRepository pollutionRepository;
    List<PollutionDto> pollutionDtoList = new ArrayList<>();

    @Autowired
    public PollutionService(RestTemplate restTemplate, PollutionRepository pollutionRepository) {
        this.restTemplate = restTemplate;
        this.pollutionRepository = pollutionRepository;
        // 서비스 생성 단계에서 기존의 데이터를 갖고와야 할듯
        // => 미세먼지, 점검 중 등의 데이터가 올바르지 않을 때 그전의 데이터 값을 보여줌
    }

    @Scheduled(fixedDelay = 300000)
    public List<PollutionDto> showAirPollutionByLocation() {
        int startPage = 1;
        int maxPage = 30;
        String url = "http://openapi.seoul.go.kr:8088/7246436c7a746e733437454d506762/json/ListAirQualityByDistrictService/"
                        + startPage + "/" + maxPage;

        try {
            log.info("\n=== api 호출 후 데이터 저장 로직 시작 ===\n");
            pollutionDtoList.clear();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            // API 호출 후 JSON 데이터 파싱
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntity.getBody());
            JSONObject jsonData = (JSONObject) jsonObject.get("ListAirQualityByDistrictService");
            JSONArray totalData = (JSONArray) jsonData.get("row");

            for(Object data : totalData){
                JSONObject object = (JSONObject) data;
                PollutionDto pollutionDto = makeDtoByJsonData(object);
                pollutionDtoList.add(pollutionDto);
                pollutionRepository.save(pollutionDto.toEntity());
            }
        } catch (Exception e) {
            log.warn("초기 API 호출 중 오류 발생");
            e.printStackTrace();
        }
        log.info("\n=== 대기현황 api 호출 끝 ===\n");
        for(PollutionDto pollutionDto : pollutionDtoList)
            System.out.println(pollutionDto);
        System.out.println("\n=============================================\n");

        return pollutionDtoList;
    }

    private static PollutionDto makeDtoByJsonData(JSONObject pollution){
        // 측정시각 기존 데이터 : ex) 202401261100, parsing 후 => 2024년 01월 26일 11시
        String dateStr = (String)pollution.get("MSRDATE");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        String parsedDate = localDateTime.
                format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시"));

        // 대시 상태 등급 정보 : [점검 중 일때 공백으로 입력됨 => 점검 중으로 변환]
        String grade = ((String)pollution.get("GRADE")).equals("")
                            ? "점검중" : (String)pollution.get("GRADE");

        PollutionDto pollutionDto = PollutionDto.builder()
                .msrCode((String) pollution.get("MSRADMCODE"))
                .msrName((String) pollution.get("MSRSTENAME"))
                .msrDate(parsedDate)
                .grade(grade)
                .pm10((String)pollution.get("PM10"))
                .pm25((String) pollution.get("PM25"))
                .build();
        return pollutionDto;
    }
}
