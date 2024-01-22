package com.sw678.api_service.model.entity;

import com.sw678.api_service.model.dto.ParkDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkId;
    @Column(length = 300, nullable = false)
    private String parkName;
    @Column(length = 300)
    private String parkAddr;
    @Column(length = 300)
    private String telNum;
    @Column(length = 300)
    private String parkLocX;
    @Column(length = 300)
    private String parkLocY;
    @Column(length = 300)
    private String parkImg;
    @Column(length = 300)
    private String parkUrl;

    public ParkDto toDto(){
        return ParkDto.builder()
                .parkId(parkId)
                .parkName(parkName)
                .parkAddr(parkAddr)
                .telNum(telNum)
                .parkLocX(parkLocX)
                .parkLocY(parkLocY)
                .parkImg(parkImg)
                .parkUrl(parkUrl)
                .build();
    }
}
