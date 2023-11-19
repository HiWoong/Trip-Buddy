package com.ssafy.enjoytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {

    private int planId;
    private String userId;
    private String subject;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private String content5;
    private String createDate;
    private String updateDate;
}
