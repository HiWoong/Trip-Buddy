package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.PlanDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanService {
    int createPlan(PlanDto planDto) throws Exception;
    List<PlanDto> viewTotalPlan(String userId) throws Exception;
    int deletePlan(String planId) throws Exception;

}
