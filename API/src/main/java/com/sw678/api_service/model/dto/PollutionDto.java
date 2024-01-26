package com.sw678.api_service.model.dto;

import com.sw678.api_service.model.entity.Park;
import com.sw678.api_service.model.entity.Pollution;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PollutionDto {

    //private Long msrId;
    private String msrCode;
    private String msrName;
    private String msrDate;
    private String grade;
    private String pm10;
    private String pm25;        // 2.5가 초미세먼지 -> 변수 작명상 소수점 안돼서 25로 정함.
    private String updateTime;

    public Pollution toEntity(){
        return Pollution.builder()
                //.msrId(msrId)
                .msrCode(msrCode)
                .msrName(msrName)
                .msrDate(msrDate)
                .grade(grade)
                .pm10(pm10)
                .pm25(pm25)
                .updateTime(updateTime)
                .build();
    }
}
