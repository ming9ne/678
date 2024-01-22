package com.sw678.api_service.model.dto;

import com.sw678.api_service.model.entity.Park;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParkDto {

    private Long parkId;
    private String parkName;
    private String parkAddr;
    private String telNum;
    private String parkLocX;
    private String parkLocY;
    private String parkImg;
    private String parkUrl;


    public Park toEntity(){
        return Park.builder()
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
