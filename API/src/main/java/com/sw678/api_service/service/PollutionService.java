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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String RESULT_CODE = "INFO-000";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RestTemplate restTemplate;
    private PollutionRepository pollutionRepository;
    private List<PollutionDto> pollutionDtoList = new ArrayList<>();
    private HttpStatusCode status;

    @Autowired
    public PollutionService(RestTemplate restTemplate, PollutionRepository pollutionRepository) {
        this.restTemplate = restTemplate;
        this.pollutionRepository = pollutionRepository;
    }

    @Scheduled(fixedDelay = 600000)
    public List<PollutionDto> showAirPollutionByLocation() {
        int startPage = 1;
        int maxPage = 30;
        String url = "http://openapi.seoul.go.kr:8088/7246436c7a746e733437454d506762/json/ListAirQualityByDistrictService/"
                + startPage + "/" + maxPage;

        try {
            log.info("\n========= parkDtoList 초기화 ===========");
            log.info("\n=== 대기현황 api 호출 후 데이터 저장 로직 시작 ===");
            pollutionDtoList.clear();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            status = responseEntity.getStatusCode();
            if (status == HttpStatus.OK
                    || status == HttpStatus.CREATED
                    || status == HttpStatus.ACCEPTED) { // 비동기 callback 아직 처리안함

                System.out.println("HttpStatus : " + status);

                // API 호출 후 JSON 데이터 파싱
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntity.getBody());
                JSONObject jsonData = (JSONObject) jsonObject.get("ListAirQualityByDistrictService");
                JSONObject jsonResult = (JSONObject) jsonData.get("RESULT");
                JSONArray totalData = (JSONArray) jsonData.get("row");

                String code = (String) jsonResult.get("CODE");

                if (jsonObject != null && code.equals(RESULT_CODE)) {
                    for (Object data : totalData) {
                        JSONObject object = (JSONObject) data;
                        PollutionDto pollutionDto = makeDtoByJsonData(object);
                        pollutionDtoList.add(pollutionDto);
                        pollutionRepository.save(pollutionDto.toEntity());
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
        log.info("\n=== 대기현황 api 호출 끝 ===\n");
        for (PollutionDto pollutionDto : pollutionDtoList)
            System.out.println(pollutionDto);
        System.out.println("\n=============================================\n");

        return pollutionDtoList;
    }


    private static PollutionDto makeDtoByJsonData(JSONObject pollution) {
        // 측정시각 기존 데이터 : ex) 202401261100, parsing 후 => 2024년 01월 26일 11시
        String dateStr = (String) pollution.get("MSRDATE");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        String parsedDate = localDateTime.
                format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시"));

        // 대시 상태 등급 정보 : [점검 중 일때 공백으로 입력됨 => 점검 중으로 변환]
        String grade = ((String) pollution.get("GRADE")).equals("")
                ? "점검중" : (String) pollution.get("GRADE");


        PollutionDto pollutionDto = PollutionDto.builder()
                .msrCode((String) pollution.get("MSRADMCODE"))
                .msrName((String) pollution.get("MSRSTENAME"))
                .msrDate(parsedDate)
                .grade(grade)
                .pm10((String) pollution.get("PM10"))
                .pm25((String) pollution.get("PM25"))
                .updateTime(now)
                .build();
        return pollutionDto;
    }
}
