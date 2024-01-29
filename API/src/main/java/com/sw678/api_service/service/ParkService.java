package com.sw678.api_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw678.api_service.model.dto.ParkDto;
import com.sw678.api_service.repository.ParkRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private ParkRepository parkRepository;
    List<ParkDto> parkDtoList;

    @Autowired
    public ParkService(RestTemplate restTemplate, ParkRepository parkRepository) {
        this.restTemplate = restTemplate;
        this.parkRepository = parkRepository;
    }

    public List<ParkDto> saveParkInfo(){
        int startPage = 1;
        int maxPage = 500;
        String url = "http://openapi.seoul.go.kr:8088/5051666379746e733737476265586d/json/SearchParkInfoService/"
                + startPage + "/" + maxPage;

        try {
            log.info("\n=== api 호출 후 데이터 저장 로직 시작 ===\n");
            parkDtoList = new ArrayList<>();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            // API 호출 후 JSON 데이터 파싱
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntity.getBody());
            JSONObject jsonData = (JSONObject) jsonObject.get("SearchParkInfoService");
            JSONArray totalData = (JSONArray) jsonData.get("row");
            //System.out.println(totalData);


            for(Object data : totalData){
                JSONObject object = (JSONObject) data;
                ParkDto parkDto = makeDtoByJsonData(object);
                parkDtoList.add(parkDto);
                parkRepository.save(parkDto.toEntity());
            }
        } catch (Exception e) {
            log.warn("초기 API 호출 중 오류 발생");
            e.printStackTrace();
        }
        log.info("\n=== api 호출 후 데이터 저장 로직 끝 ===");
        return parkDtoList;
    }



    private static ParkDto makeDtoByJsonData(JSONObject park){
        ParkDto parkDto = ParkDto.builder()
                            .parkId(Long.parseLong((String) park.get("P_IDX")))
                            .parkName((String) park.get("P_PARK"))
                            .parkAddr((String) park.get("P_ADDR"))
                            .telNum((String) park.get("P_ADMINTEL"))
                            .parkLocX((String)park.get("LONGITUDE"))
                            .parkLocY((String)park.get("LATITUDE"))
                            .parkImg((String) park.get("P_IMG"))
                            .parkUrl((String) park.get("TEMPLATE_URL"))
                            .build();
        return parkDto;
    }
}
