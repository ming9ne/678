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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RestTemplate restTemplate;
    private static final String RESULT_CODE = "INFO-000";
    private List<ParkDto> parkDtoList = new ArrayList<>();
    private HttpStatusCode status;
    private ParkRepository parkRepository;

    @Autowired
    public ParkService(RestTemplate restTemplate, ParkRepository parkRepository) {
        this.restTemplate = restTemplate;
        this.parkRepository = parkRepository;
    }

    public List<ParkDto> saveParkInfo() {
        int startPage = 1;
        int maxPage = 500;
        String url = "http://openapi.seoul.go.kr:8088/5051666379746e733737476265586d/json/SearchParkInfoService/"
                + startPage + "/" + maxPage;

        try {
            parkDtoList.clear();
            log.info("\n========= parkDtoList 초기화 ===========");
            log.info("\n========= 공원 api 호출 후 데이터 저장 로직 시작 =========");

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            status = responseEntity.getStatusCode();
            if (status == HttpStatus.OK
                    || status == HttpStatus.CREATED
                    || status == HttpStatus.ACCEPTED) { // 비동기 callback 아직 처리안함

                System.out.println("HttpStatus : " + status);

                // API 호출 후 JSON 데이터 파싱
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntity.getBody());
                JSONObject jsonData = (JSONObject) jsonObject.get("SearchParkInfoService");
                JSONObject jsonResult = (JSONObject) jsonData.get("RESULT");
                JSONArray totalData = (JSONArray) jsonData.get("row");

                String code = (String) jsonResult.get("CODE");

                if (jsonObject != null && code.equals(RESULT_CODE)) {
                    for (Object data : totalData) {
                        JSONObject object = (JSONObject) data;
                        ParkDto parkDto = makeDtoByJsonData(object);
                        parkDtoList.add(parkDto);
                        parkRepository.save(parkDto.toEntity());
                    }
                } else {
                    log.warn("API 호출 실패");
                    System.out.println("요청 결과 데이터 오류. 코드값 : " + code);
                    return null;
                }
            } else {
                log.warn("API 호출 실패");
                System.out.println("API 호출 실패. 상태코드 : " + status);
                return null;
            }
        } catch (Exception e) {
            log.warn("초기 API 호출 중 오류 발생");
            System.out.println("오류 메시지 : " + e.getMessage());
            return null;
        }
        log.info("\n=== api 호출 후 데이터 저장 로직 끝 ===");
        return parkDtoList;
    }

    private static ParkDto makeDtoByJsonData(JSONObject park) {
        ParkDto parkDto = ParkDto.builder()
                .parkId(Long.parseLong((String) park.get("P_IDX")))
                .parkName((String) park.get("P_PARK"))
                .parkAddr((String) park.get("P_ADDR"))
                .telNum((String) park.get("P_ADMINTEL"))
                .parkLocX((String) park.get("G_LONGITUDE"))
                .parkLocY((String) park.get("G_LATITUDE"))
                .parkImg((String) park.get("P_IMG"))
                .parkUrl((String) park.get("TEMPLATE_URL"))
                .build();
        return parkDto;
    }
}
