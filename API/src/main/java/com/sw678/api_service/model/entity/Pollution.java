package com.sw678.api_service.model.entity;

import com.sw678.api_service.model.dto.PollutionDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Pollution {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long msrId;
    @Id
    @Column(length = 255, nullable = false)
    private String msrCode;
    @Column(length = 255)
    private String msrName;
    @Column(length = 255)
    private String msrDate;
    @Column(length = 255)
    private String grade;
    @Column(length = 255)
    private String pm10;
    @Column(length = 255)
    private String pm25;        // 2.5가 초미세먼지 -> 변수 작명상 소수점 안돼서 25로 정함.
    @Column(length = 255)
    private String updateTime;

    public PollutionDto toDto(){
        return PollutionDto.builder()
               // .msrId(msrId)
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
